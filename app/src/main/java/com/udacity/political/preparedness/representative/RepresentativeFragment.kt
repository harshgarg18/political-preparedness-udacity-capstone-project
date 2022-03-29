package com.udacity.political.preparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.udacity.political.preparedness.MyApp
import com.udacity.political.preparedness.R
import com.udacity.political.preparedness.databinding.FragmentRepresentativeBinding
import com.udacity.political.preparedness.network.models.Address
import com.udacity.political.preparedness.representative.adapter.RepresentativeListAdapter
import timber.log.Timber
import java.util.*

class RepresentativeFragment : Fragment() {

    companion object {
        private val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val viewModel: RepresentativeViewModel by viewModels {
        val app = requireContext().applicationContext as MyApp
        RepresentativeViewModel.Factory(app)
    }

    private lateinit var binding: FragmentRepresentativeBinding
    private lateinit var permissionRequestLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var locationIntentLauncher: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_representative, container, false
        )
        binding.executePendingBindings()
        viewModel.retrieveDataFromSavedState(savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        registerPermissionObjects()

        binding.representativesRecycler.adapter = RepresentativeListAdapter().apply {
            viewModel.representatives.observe(viewLifecycleOwner, this::submitList)
        }

        binding.findRepresentativesButton.setOnClickListener {
            hideKeyboard()
            viewModel.fetchRepresentatives()
        }

        binding.useLocationButton.setOnClickListener {
            hideKeyboard()
            getLocationAndFindRepresentatives()
        }

        viewModel.selectedStatePosition.observe(viewLifecycleOwner) {
            viewModel.setState(it)
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(viewModel.saveDataToState(outState))
    }

    private fun getLocationAndFindRepresentatives() {
        if (!arePermissionsGranted()) {
            requestLocationPermissions()
            return
        }

        getDeviceLocationSettingsAndFetchLocation()
    }

    private fun registerPermissionObjects() {
        permissionRequestLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                val result = getPermissionResult(it, locationPermissions)
                when {
                    result.allGranted -> getLocationAndFindRepresentatives()
                    result.shouldShowPermissionRationale -> showLocationRationale()
                    else -> binding.useLocationButton.isEnabled = false
                }
            }
        locationIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                getDeviceLocationSettingsAndFetchLocation(false)
            }
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun requestLocationPermissions() {
        permissionRequestLauncher.launch(locationPermissions)
    }

    private fun arePermissionsGranted(): Boolean = locationPermissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showLocationRationale() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.location_permission)
            .setMessage(R.string.location_permission_explanation)
            .show()
    }

    private fun getDeviceLocationSettingsAndFetchLocation(resolve: Boolean = true) {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        val locationSettingsResponseTask =
            settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnFailureListener {
            if (it is ResolvableApiException && resolve) {
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(it.resolution).build()
                    locationIntentLauncher.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) {
                    Timber.e("Error getting location settings resolution: ${e.message}")
                }
            } else {
                Snackbar.make(
                    requireView(),
                    getString(R.string.location_required_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        locationSettingsResponseTask.addOnCompleteListener {
            if (it.isSuccessful) {
                registerLocationUpdates()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun registerLocationUpdates() {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                fetchRepresentatives(geoCodeLocation(location))
            }
        }

        Looper.myLooper()?.let {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                it
            )
        }
    }

    private fun fetchRepresentatives(address: Address) {
        viewModel.setAddress(address)
        viewModel.fetchRepresentatives()
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .map { address ->
                Address(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
            .first()
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}
