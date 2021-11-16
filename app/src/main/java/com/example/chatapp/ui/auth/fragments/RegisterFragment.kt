package com.example.chatapp.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.core.Result
import com.example.chatapp.core.hide
import com.example.chatapp.core.show
import com.example.chatapp.data.remote.auth.AuthDataSource
import com.example.chatapp.databinding.FragmentRegisterBinding
import com.example.chatapp.domain.auth.AuthRepoImpl
import com.example.chatapp.presentation.auth.AuthViewModel
import com.example.chatapp.presentation.auth.AuthViewModelFactory
import com.example.chatapp.ui.home.MainActivity

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

        binding.btnSignUp.setOnClickListener {
            val email = binding.txtEmail.text.toString().trim()
            val displayName = binding.txtDisplayName.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            viewModel.singUp(email, password, displayName)
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.show()
                        }
                        is Result.Success -> {
                            binding.progressBar.hide()
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                        is Result.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "${result.exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.progressBar.hide()
                        }
                    }
                })

        }

    }
}