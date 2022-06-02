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
import com.example.chatapp.data.local.LocalHomeDataSource
import com.example.chatapp.data.model.AppDatabase
import com.example.chatapp.data.remote.home.HomeDataSource
import com.example.chatapp.databinding.FragmentPeopleBinding
import com.example.chatapp.domain.home.HomeRepoImpl
import com.example.chatapp.presentation.home.HomeViewModel
import com.example.chatapp.presentation.home.HomeViewModelFactory
import com.example.chatapp.ui.home.adapters.ChatsAdapter
import com.example.chatapp.ui.home.adapters.PeopleAdapter

class PeopleFragment : Fragment(R.layout.fragment_people) {

    private lateinit var binding: FragmentPeopleBinding
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

        binding = FragmentPeopleBinding.bind(view)

        viewModel.getUsers().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    binding.rvPeople.adapter = PeopleAdapter(result.data)
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