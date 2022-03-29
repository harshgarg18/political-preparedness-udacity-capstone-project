package com.udacity.political.preparedness.network.models

import com.udacity.political.preparedness.representative.model.Representative

data class RepresentativeResponse(
    val offices: List<Office>,
    val officials: List<Official>
) {
    val representatives: List<Representative>
        get() = offices.flatMap { it.getRepresentatives(officials) }
}
