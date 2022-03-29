package com.udacity.political.preparedness.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    var line1: String,
    var line2: String? = null,
    var city: String,
    var state: String,
    var zip: String
) : Parcelable {
    fun toFormattedString(): String {
        val output = StringBuilder()
        output.append("$line1\n")
        if (!line2.isNullOrEmpty()) {
            output.append("$line2\n")
        }
        output.append("$city, $state $zip")
        return output.toString()
    }

    companion object {
        fun empty(): Address = Address("", "", "", "", "")
    }
}
