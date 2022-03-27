package com.udacity.political.preparedness.network.models

data class Address(
    val line1: String,
    val line2: String? = null,
    val city: String,
    val state: String,
    val zip: String
) {
    fun toFormattedString(): String {
        val output = StringBuilder()
        output.append("$line1\n")
        if (!line2.isNullOrEmpty()) {
            output.append("$line2\n")
        }
        output.append("$city, $state $zip")
        return output.toString()
    }
}
