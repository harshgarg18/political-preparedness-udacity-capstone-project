package com.udacity.political.preparedness.representative

import androidx.fragment.app.Fragment

data class PermissionResult(
    val allGranted: Boolean,
    val shouldShowPermissionRationale: Boolean
)

fun Fragment.getPermissionResult(
    result: Map<String, Boolean>,
    permissions: Array<String>
): PermissionResult {
    val granted = permissions.fold(true) { acc, p ->
        acc && result.getOrDefault(p, false)
    }

    val showRationale = permissions.fold(false) { acc, p ->
        acc || shouldShowRequestPermissionRationale(p)
    }

    return PermissionResult(granted, showRationale)
}
