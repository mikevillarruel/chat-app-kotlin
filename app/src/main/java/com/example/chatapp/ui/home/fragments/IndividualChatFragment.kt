package com.example.chatapp.ui.home.fragments

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.chatapp.R
import com.example.chatapp.core.hide
import com.example.chatapp.core.show
import com.example.chatapp.data.model.User
import com.example.chatapp.databinding.FragmentIndividualChatBinding

class IndividualChatFragment : Fragment(R.layout.fragment_individual_chat) {

    private lateinit var binding: FragmentIndividualChatBinding
    private lateinit var user: User
    private val arguments: IndividualChatFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentIndividualChatBinding.bind(view)
        user = arguments.user
        binding.txtUsername.text = user.displayName

        binding.txtMessage.addTextChangedListener { text: Editable? ->
            if (text.toString().isNullOrEmpty()) {
                binding.btnMicrophone.show()
                binding.btnCamera.show()
                binding.btnSend.hide()
            } else {
                binding.btnMicrophone.hide()
                binding.btnCamera.hide()
                binding.btnSend.show()
            }
        }

    }
}