package com.obidia.marketapp.di

import android.app.Application
import androidx.room.Room
import com.obidia.marketapp.data.local.StockDatabase
import com.obidia.marketapp.data.remote.dto.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideStockApi(): StockApi {
    return Retrofit.Builder()
      .baseUrl(StockApi.BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
      .create()
  }

  @Provides
  @Singleton
  fun provideStockDatabase(app: Application): StockDatabase {
    return Room.databaseBuilder(
      app,
      StockDatabase::class.java,
      "stock.db"
    ).build()
  }
}