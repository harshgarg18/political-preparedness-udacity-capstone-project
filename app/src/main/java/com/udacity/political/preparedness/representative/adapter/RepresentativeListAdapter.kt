package com.udacity.political.preparedness.representative.adapter

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.political.preparedness.databinding.ListItemRepresentativeBinding
import com.udacity.political.preparedness.network.models.Channel
import com.udacity.political.preparedness.representative.model.Representative


class RepresentativeListAdapter :
    ListAdapter<Representative, RepresentativeViewHolder>(RepresentativeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepresentativeViewHolder {
        return RepresentativeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepresentativeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class RepresentativeViewHolder(private val binding: ListItemRepresentativeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): RepresentativeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemRepresentativeBinding.inflate(layoutInflater, parent, false)
            return RepresentativeViewHolder(binding)
        }
    }

    fun bind(item: Representative) {
        binding.representative = item
        binding.executePendingBindings()
        showSocialLinks(item.official.channels)
        showWWWLinks(item.official.urls)
    }

    private fun showSocialLinks(channels: List<Channel>?) {
        channels?.let {
            with(binding.facebookIcon) {
                val facebookUrl = getFacebookUrl(channels)
                if (!facebookUrl.isNullOrBlank()) {
                    enableLink(this, facebookUrl)
                } else {
                    disableLink(this)
                }
            }

            with(binding.twitterIcon) {
                val twitterUrl = getTwitterUrl(channels)
                if (!twitterUrl.isNullOrBlank()) {
                    enableLink(this, twitterUrl)
                } else {
                    disableLink(this)
                }
            }
        }
    }

    private fun showWWWLinks(urls: List<String>?) {
        with(binding.wwwIcon) {
            val url = urls?.first()
            if (url != null) {
                enableLink(this, url)
            } else {
                disableLink(this)
            }
        }
    }

    private fun getFacebookUrl(channels: List<Channel>): String? {
        return channels.filter { channel -> channel.type == "Facebook" }
            .map { channel -> "https://www.facebook.com/${channel.id}" }
            .firstOrNull()
    }

    private fun getTwitterUrl(channels: List<Channel>): String? {
        return channels.filter { channel -> channel.type == "Twitter" }
            .map { channel -> "https://www.twitter.com/${channel.id}" }
            .firstOrNull()
    }

    private fun enableLink(view: ImageView, url: String) {
        view.visibility = View.VISIBLE
        view.setOnClickListener { setIntent(url) }
    }

    private fun disableLink(view: ImageView) {
        view.visibility = View.GONE
    }

    private fun setIntent(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(ACTION_VIEW, uri)
        itemView.context.startActivity(intent)
    }
}

object RepresentativeDiffCallback : DiffUtil.ItemCallback<Representative>() {
    override fun areItemsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem == newItem
    }
}
