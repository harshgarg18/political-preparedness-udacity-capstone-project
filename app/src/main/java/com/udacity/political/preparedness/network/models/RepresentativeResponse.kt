package com.udacity.political.preparedness.network.models

data class RepresentativeResponse(
    val offices: List<Office>,
    val officials: List<Official>
)
