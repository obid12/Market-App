package com.obidia.marketapp.domain.repository

import com.obidia.marketapp.domain.model.CompanyListing
import com.obidia.marketapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
  suspend fun getCompanyListings(
    fetchFromRemote: Boolean,
    query: String
  ): Flow<Resource<List<CompanyListing>>>
}