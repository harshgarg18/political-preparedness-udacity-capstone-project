package com.udacity.political.preparedness.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.udacity.political.preparedness.R

@BindingAdapter("visibleIf")
fun booleanToVisibility(view: View, isVisible: Boolean?) {
    view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
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
fun fetchImage(imageView: ImageView, src: String?) {
    if (src != null) {
        val uri = src.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(uri)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_profile)
            .circleCrop()
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.ic_profile)
    }
}
