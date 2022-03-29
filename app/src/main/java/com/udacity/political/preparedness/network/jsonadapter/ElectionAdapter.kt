package com.udacity.political.preparedness.network.jsonadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.udacity.political.preparedness.network.models.Division

class ElectionAdapter {
    @FromJson
    fun divisionFromJson(ocdDivisionId: String): Division {
        val countryDelimiter = "country:"
        val stateDelimiter = "state:"
        val districtDelimiter = "district:"
        val country = ocdDivisionId.substringAfter(countryDelimiter, "")
            .substringBefore("/")
        val state = ocdDivisionId.substringAfter(stateDelimiter, "")
            .substringBefore("/")
        val district = ocdDivisionId.substringAfter(districtDelimiter, "")
            .substringBefore("/")
        val stateVal = state.ifBlank { district }
        return Division(ocdDivisionId, country, stateVal)
    }

    @ToJson
    fun divisionToJson(division: Division): String {
        return division.id
    }
}
