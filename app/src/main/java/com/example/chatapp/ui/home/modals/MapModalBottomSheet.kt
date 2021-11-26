package com.example.chatapp.ui.home.modals

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.chatapp.R
import com.example.chatapp.core.Result
import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.MessageType
import com.example.chatapp.data.model.User
import com.example.chatapp.data.remote.home.HomeDataSource
import com.example.chatapp.databinding.ModalBottomSheetMapBinding
import com.example.chatapp.domain.home.HomeRepoImpl
import com.example.chatapp.presentation.home.HomeViewModel
import com.example.chatapp.presentation.home.HomeViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MapModalBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: ModalBottomSheetMapBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(
                HomeDataSource()
            )
        )
    }
    private var userLatLng: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modal_bottom_sheet_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ModalBottomSheetMapBinding.bind(view)

        loadLocation()

        binding.btnSendLocation.setOnClickListener {
            val user: User? = arguments?.getParcelable<User>("user")
            user?.let {
                viewModel.sendMessage(
                    Message(
                        uid = Firebase.auth.currentUser?.uid.toString(),
                        content = userLatLng,
                        type = MessageType.LOCATION.value
                    ), user.uid
                ).observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            this.dismiss()
                        }
                        is Result.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "${result.exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun loadLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when {
                    permissions.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        false
                    ) || permissions.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        false
                    ) -> {
                        fusedLocationClient
                            .lastLocation
                            .addOnSuccessListener { location: Location? ->
                                val mapFragment =
                                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                                mapFragment.getMapAsync { map ->
                                    if (location != null) {
                                        val markerLatLng = LatLng(
                                            location.latitude,
                                            location.longitude
                                        )

                                        userLatLng = markerLatLng

                                        map.addMarker(
                                            MarkerOptions()
                                                .position(markerLatLng)
                                                .title("My Location")
                                        )
                                        map.moveCamera(
                                            CameraUpdateFactory.newLatLngZoom(
                                                markerLatLng,
                                                10f
                                            )
                                        )
                                        map.animateCamera(CameraUpdateFactory.zoomIn())
                                        map.animateCamera(
                                            CameraUpdateFactory.zoomTo(15f),
                                            1000,
                                            null
                                        )
                                    }
                                }
                            }
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "Grant location permission to send your location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


    companion object {
        const val TAG = "MapModalBottomSheet"
    }

}