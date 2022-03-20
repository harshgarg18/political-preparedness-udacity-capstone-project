package com.udacity.political.preparedness.representative.model

import com.udacity.political.preparedness.network.models.Office
import com.udacity.political.preparedness.network.models.Official

data class Representative(
    val official: Official,
    val office: Office
)
