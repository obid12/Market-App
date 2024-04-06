package com.obidia.marketapp.data.mapper

import com.obidia.marketapp.data.local.CompanyListingEntity
import com.obidia.marketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
  return CompanyListing(
    name = name,
    symbol = symbol,
    exchange = exchange
  )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
  return CompanyListingEntity(
    name = name,
    symbol = symbol,
    exchange = exchange
  )
}