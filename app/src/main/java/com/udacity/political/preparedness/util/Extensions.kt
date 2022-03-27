package com.udacity.political.preparedness.util

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun Toolbar.allowMultiLineText() {
    for (i in 0..childCount) {
        val child = getChildAt(i)
        if (child is TextView) {
            child.isSingleLine = false
        }
    }
}

fun <T, K, R> LiveData<T>.combineWith(
    liveData: LiveData<K>,
    initialValue: R,
    combine: (T?, K?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>().apply {
        value = initialValue
    }
    result.addSource(this) {
        result.value = combine(this.value, liveData.value)
    }
    result.addSource(liveData) {
        result.value = combine(this.value, liveData.value)
    }

    return result
}
