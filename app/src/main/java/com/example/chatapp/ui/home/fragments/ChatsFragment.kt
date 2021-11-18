package com.example.chatapp.ui.home.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.core.Result
import com.example.chatapp.data.model.User
import com.example.chatapp.data.remote.home.HomeDataSource
import com.example.chatapp.databinding.FragmentChatsBinding
import com.example.chatapp.domain.home.HomeRepoImpl
import com.example.chatapp.presentation.home.HomeViewModel
import com.example.chatapp.presentation.home.HomeViewModelFactory
import com.example.chatapp.ui.home.adapters.ChatsAdapter

class ChatsFragment : Fragment(R.layout.fragment_chats) {

    private lateinit var binding: FragmentChatsBinding
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(
                HomeDataSource()
            )
        )
    }

    private fun onClick(item: User) {
        Toast.makeText(requireContext(), "${item.displayName}", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatsBinding.bind(view)

        binding.btnWrite.setOnClickListener {
            findNavController().navigate(R.id.action_chatsFragment_to_chatFragment)
        }

        viewModel.getUsers().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    binding.rvChats.adapter = ChatsAdapter(result.data, onClick = { user ->
                        onClick(user)
                    })
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