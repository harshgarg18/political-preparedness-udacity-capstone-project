package com.udacity.political.preparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.udacity.political.preparedness.MyApp
import com.udacity.political.preparedness.R
import com.udacity.political.preparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    private val args by navArgs<VoterInfoFragmentArgs>()
    private val viewModel: VoterInfoViewModel by viewModels {
        val app = requireContext().applicationContext as MyApp
        VoterInfoViewModel.Factory(app, args.election)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentVoterInfoBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_voter_info, container, false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.election = args.election

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = args.election.name
        }

        viewModel.urlToBeOpened.observe(viewLifecycleOwner) {
            if (it != null) {
                loadUrlIntent(it)
                viewModel.onUrlLoaded()
            }
        }

        return binding.root
    }

    private fun loadUrlIntent(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }
}
