package com.udacity.political.preparedness.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Division(
    val id: String,
    val country: String,
    val state: String
) : Parcelable {
    fun format(): String = listOf(state, country).filter { it.isNotEmpty() }.joinToString()
}
