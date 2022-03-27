package com.udacity.political.preparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.political.preparedness.databinding.ListItemElectionBinding
import com.udacity.political.preparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener) :
    ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val election = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(election)
        }
        holder.bind(election)
    }
}

class ElectionViewHolder(private val binding: ListItemElectionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(election: Election) {
        binding.election = election
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemElectionBinding.inflate(layoutInflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }
}

object ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

}

class ElectionListener(private val clickListener: (election: Election) -> Unit) {
    fun onClick(election: Election) = clickListener(election)
}
