package com.udacity.political.preparedness.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElectionResponse(
    val kind: String,
    val elections: List<Election>
)
