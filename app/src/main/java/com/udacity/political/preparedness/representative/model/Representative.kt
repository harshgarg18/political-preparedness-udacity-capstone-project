package com.udacity.political.preparedness.representative.model

import android.os.Parcelable
import com.udacity.political.preparedness.network.models.Office
import com.udacity.political.preparedness.network.models.Official
import kotlinx.parcelize.Parcelize

@Parcelize
data class Representative(
    val official: Official,
    val office: Office
) : Parcelable
