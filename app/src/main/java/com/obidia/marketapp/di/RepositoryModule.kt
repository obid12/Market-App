package com.obidia.marketapp.di

import com.obidia.marketapp.data.csv.CSVParser
import com.obidia.marketapp.data.csv.CompanyListingsParser
import com.obidia.marketapp.data.repository.StockRepositoryImplementation
import com.obidia.marketapp.domain.model.CompanyListing
import com.obidia.marketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  @Singleton
  abstract fun bindsCompanyListingParser(
    companyListingsParser: CompanyListingsParser
  ): CSVParser<CompanyListing>

  @Binds
  @Singleton
  abstract fun bindsStockRepository(
    stockRepositoryImplementation: StockRepositoryImplementation
  ): StockRepository
}