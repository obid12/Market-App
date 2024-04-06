package com.obidia.marketapp.data.repository

import com.obidia.marketapp.data.csv.CSVParser
import com.obidia.marketapp.data.local.StockDatabase
import com.obidia.marketapp.data.mapper.toCompanyListing
import com.obidia.marketapp.data.mapper.toCompanyListingEntity
import com.obidia.marketapp.data.remote.dto.StockApi
import com.obidia.marketapp.domain.model.CompanyListing
import com.obidia.marketapp.domain.repository.StockRepository
import com.obidia.marketapp.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

@Singleton
class StockRepositoryImplementation @Inject constructor(
  private val api: StockApi,
  private val db: StockDatabase,
  private val companyListingsParser: CSVParser<CompanyListing>
) : StockRepository {
  override suspend fun getCompanyListings(
    fetchFromRemote: Boolean,
    query: String
  ): Flow<Resource<List<CompanyListing>>> {
    return flow {
      emit(Resource.Loading(true))
      val localListing = db.dao.searchCompanyListing(query)
      emit(Resource.Success(
        data = localListing.map { it.toCompanyListing() }
      ))

      val isDbEmpty = localListing.isEmpty() && query.isBlank()
      val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
      if (shouldJustLoadFromCache) {
        emit(Resource.Loading(false))
        return@flow
      }

      val remoteListing = try {
        val response = api.getListings()
        companyListingsParser.parse(response.byteStream())
      } catch (e: Exception) {
        e.printStackTrace()
        when (e) {
          is IOException -> {
            emit(Resource.Error("Couldn't load data"))
            null
          }

          is HttpException -> {
            emit(Resource.Error("Couldn't load data"))
            null
          }

          else -> null
        }
      }

      remoteListing?.let { listings ->
        db.dao.clearCompanyListings()
        db.dao.insertCompanyListings(
          listings.map { it.toCompanyListingEntity() }
        )
        emit(Resource.Success(
          data = db.dao
            .searchCompanyListing("")
            .map { it.toCompanyListing() }
        ))
        emit(Resource.Loading(false))
      }
    }
  }
}