package com.example.chatapp.ui.home.fragments

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.core.hide
import com.example.chatapp.core.show
import com.example.chatapp.databinding.FragmentIndividualChatBinding

class IndividualChatFragment : Fragment(R.layout.fragment_individual_chat) {

    lateinit var binding: FragmentIndividualChatBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentIndividualChatBinding.bind(view)

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