package com.example.chatapp.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.chatapp.data.model.Message
import com.example.chatapp.databinding.IncomingMessageItemBinding
import com.example.chatapp.databinding.OutgoingMessageItemBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class IndividualChatAdapter(
    private val messageList: List<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val OUTGOING = 0
    val INCOMING = 1

    class ViewHolderOutgoing(
        private val binding: OutgoingMessageItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.txtMessage.text = item.content.toString()
        }
    }

    class ViewHolderIncoming(
        private val binding: IncomingMessageItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.txtMessage.text = item.content.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding: ViewBinding

        if (viewType == INCOMING) {
            itemBinding =
                IncomingMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ViewHolderIncoming(itemBinding, parent.context)
        } else {
            itemBinding =
                OutgoingMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ViewHolderOutgoing(itemBinding, parent.context)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == INCOMING) {
            (holder as ViewHolderIncoming).bind(messageList[position])
        } else {
            (holder as ViewHolderOutgoing).bind(messageList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (messageList[position].uid == Firebase.auth.currentUser?.uid.toString()) {
            return OUTGOING
        } else {
            return INCOMING
        }
    }

    override fun getItemCount(): Int = messageList.size
}