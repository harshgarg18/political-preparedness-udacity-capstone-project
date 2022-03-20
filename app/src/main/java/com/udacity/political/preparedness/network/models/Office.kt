package com.udacity.political.preparedness.network.models

import com.squareup.moshi.Json
import com.udacity.political.preparedness.representative.model.Representative

data class Office(
    val name: String,
    @Json(name = "divisionId") val division: Division,
    @Json(name = "officialIndices") val officials: List<Int>
) {
    fun getRepresentatives(officials: List<Official>): List<Representative> {
        return this.officials.map { index ->
            Representative(officials[index], this)
        }
    }
}
