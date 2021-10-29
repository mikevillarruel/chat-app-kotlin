package com.example.chatapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentChatsBinding

class ChatsFragment : Fragment(R.layout.fragment_chats) {

    lateinit var binding: FragmentChatsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatsBinding.bind(view)

        binding.btnWrite.setOnClickListener {
            findNavController().navigate(R.id.action_chatsFragment_to_chatFragment)
        }
    }
}