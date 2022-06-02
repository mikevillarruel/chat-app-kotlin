package com.example.chatapp.ui.home.fragments

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.chatapp.R
import com.example.chatapp.core.Result
import com.example.chatapp.core.hide
import com.example.chatapp.core.show
import com.example.chatapp.data.local.LocalHomeDataSource
import com.example.chatapp.data.model.AppDatabase
import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.MessageType
import com.example.chatapp.data.model.User
import com.example.chatapp.data.remote.home.HomeDataSource
import com.example.chatapp.databinding.FragmentIndividualChatBinding
import com.example.chatapp.domain.home.HomeRepoImpl
import com.example.chatapp.presentation.home.HomeViewModel
import com.example.chatapp.presentation.home.HomeViewModelFactory
import com.example.chatapp.ui.home.adapters.IndividualChatAdapter
import com.example.chatapp.ui.home.modals.MapModalBottomSheet
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class IndividualChatFragment : Fragment(R.layout.fragment_individual_chat) {

    private lateinit var binding: FragmentIndividualChatBinding
    private lateinit var user: User
    private val arguments: IndividualChatFragmentArgs by navArgs()
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(
                HomeDataSource(),
                LocalHomeDataSource(
                    AppDatabase.getDatabase(requireContext()).userDao()
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentIndividualChatBinding.bind(view)
        user = arguments.user
        binding.txtUsername.text = user.displayName

        binding.btnLocation.setOnClickListener {
            val modalBottomSheet = MapModalBottomSheet()
            modalBottomSheet.arguments = bundleOf("user" to user)
            modalBottomSheet.show(childFragmentManager, MapModalBottomSheet.TAG)
        }

        binding.txtMessage.addTextChangedListener { text: Editable? ->
            if (text.toString().isNullOrEmpty()) {
                binding.btnSend.hide()
                binding.btnMicrophone.show()
                binding.btnCamera.show()
                binding.btnLocation.show()
            } else {
                binding.btnSend.show()
                binding.btnMicrophone.hide()
                binding.btnCamera.hide()
                binding.btnLocation.hide()
            }
        }

        binding.btnSend.setOnClickListener {
            viewModel.sendMessage(
                Message(
                    uid = Firebase.auth.currentUser?.uid.toString(),
                    content = binding.txtMessage.text.toString().trim(),
                    type = MessageType.TEXT.value
                ), user.uid
            ).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.txtMessage.text = null
                    }
                    is Result.Success -> {
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

        viewModel.getChat(user.uid).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    binding.rvMessages.adapter = IndividualChatAdapter(result.data)
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