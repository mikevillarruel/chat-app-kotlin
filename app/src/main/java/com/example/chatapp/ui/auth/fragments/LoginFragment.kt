package com.example.chatapp.ui.auth.fragments

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
import com.example.chatapp.databinding.FragmentLoginBinding
import com.example.chatapp.domain.auth.AuthRepoImpl
import com.example.chatapp.presentation.auth.AuthViewModel
import com.example.chatapp.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        Firebase.auth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.txtEmail.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()
            viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.show()
                    }
                    is Result.Success -> {
                        binding.progressBar.hide()
                        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                        Toast.makeText(
                            requireContext(),
                            "Welcome ${result.data?.email}",
                            Toast.LENGTH_SHORT
                        ).show()
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