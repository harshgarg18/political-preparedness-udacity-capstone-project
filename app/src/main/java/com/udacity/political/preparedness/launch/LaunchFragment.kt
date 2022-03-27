package com.udacity.political.preparedness.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.political.preparedness.R
import com.udacity.political.preparedness.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLaunchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_launch, container, false
        )
        binding.lifecycleOwner = this

        binding.representativeButton.setOnClickListener { navToRepresentatives() }
        binding.upcomingButton.setOnClickListener { navToElections() }

        return binding.root
    }

    private fun navToElections() {
        this.findNavController().navigate(LaunchFragmentDirections.toElectionsFragment())
    }

    private fun navToRepresentatives() {
        this.findNavController().navigate(LaunchFragmentDirections.toRepresentativeFragment())
    }

}
