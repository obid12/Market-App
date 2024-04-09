package com.obidia.marketapp.presentation.companylistings

import com.obidia.marketapp.domain.model.CompanyListing

data class CompanyListingsState(
  val companies: List<CompanyListing> = emptyList(),
  val isLoading: Boolean = false,
  val isRefreshing: Boolean = false,
  val searchQuery: String = "",
)
