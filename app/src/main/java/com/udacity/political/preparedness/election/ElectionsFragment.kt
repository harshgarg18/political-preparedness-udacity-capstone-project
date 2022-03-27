package com.udacity.political.preparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.political.preparedness.MyApp
import com.udacity.political.preparedness.R
import com.udacity.political.preparedness.databinding.FragmentElectionBinding
import com.udacity.political.preparedness.election.adapter.ElectionListAdapter
import com.udacity.political.preparedness.election.adapter.ElectionListener
import com.udacity.political.preparedness.network.models.Election

class ElectionsFragment : Fragment() {

    private val viewModel: ElectionsViewModel by viewModels {
        val app = requireContext().applicationContext as MyApp
        ElectionsViewModel.Factory(app)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentElectionBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_election, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupListAdapter(binding.upcomingElectionsRecycler, viewModel.elections)
        setupListAdapter(binding.savedElectionsRecycler, viewModel.followedElections)

        viewModel.navigateToSelectedElection.observe(viewLifecycleOwner) {
            it?.let {
                navToVoterInfo(it)
            }
        }

        return binding.root
    }

    private fun setupListAdapter(recyclerView: RecyclerView, dataSource: LiveData<List<Election>>) {
        recyclerView.adapter = ElectionListAdapter(ElectionListener {
            viewModel.displayElectionDetails(it)
        }).apply {
            dataSource.observe(viewLifecycleOwner, this::submitList)
        }
    }

    private fun navToVoterInfo(election: Election) {
        findNavController().navigate(ElectionsFragmentDirections.toVoterInfoFragment(election))
        viewModel.displayElectionDetailsCompleted()
    }
}
