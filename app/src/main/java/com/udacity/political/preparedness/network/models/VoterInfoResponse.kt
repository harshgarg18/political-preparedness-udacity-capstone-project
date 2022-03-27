package com.udacity.political.preparedness.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse(val state: List<State>? = null)
