package com.obidia.marketapp.presentation.companylistings

sealed class CompanyListingsEvent {
  data object Refresh : CompanyListingsEvent()
  data class OnSearchQueryChange(val query: String) : CompanyListingsEvent()
}