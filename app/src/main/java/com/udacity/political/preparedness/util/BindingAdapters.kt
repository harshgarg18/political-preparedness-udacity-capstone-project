package com.udacity.political.preparedness.util

import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.political.preparedness.election.adapter.ElectionListAdapter
import com.udacity.political.preparedness.network.models.Election

@BindingAdapter("isVisible")
fun booleanToVisibility(view: View, isVisible: Boolean?) {
    view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
    data?.let {
        val adapter = recyclerView.adapter as ElectionListAdapter
        adapter.submitList(it)
    }
}

@BindingAdapter("apiStatus")
fun bindApiStatus(progressBar: ProgressBar, status: NetworkStatus?) {
    status?.let {
        when (it) {
            NetworkStatus.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            NetworkStatus.DONE -> {
                progressBar.visibility = View.GONE
            }
        }
    }
}

@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
    src?.let {
        val uri = src.toUri().buildUpon().scheme("https").build()
        //TODO: Add Glide call to load image and circle crop - user ic_profile as a placeholder and for errors.
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T> {
    return adapter as ArrayAdapter<T>
}
