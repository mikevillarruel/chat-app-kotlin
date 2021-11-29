package com.example.chatapp.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.chatapp.core.hide
import com.example.chatapp.core.show
import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.MessageType
import com.example.chatapp.databinding.IncomingMessageItemBinding
import com.example.chatapp.databinding.OutgoingMessageItemBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
            when (item.type) {
                MessageType.TEXT.value -> {
                    binding.txtMessage.text = item.content
                }
                MessageType.LOCATION.value -> {
                    binding.txtMessage.hide()
                    binding.mapContainer.show()

                    binding.map.onCreate(null)
                    binding.map.onResume()

                    item.latLng?.let { location ->
                        binding.map.getMapAsync { map ->
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(location.latitude, location.longitude),
                                    15f
                                )
                            )
                            map.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    )
                                )
                            )
                            map.mapType = GoogleMap.MAP_TYPE_NORMAL
                            map.uiSettings.setAllGesturesEnabled(false)
                        }
                    }
                }
            }
        }
    }

    class ViewHolderIncoming(
        private val binding: IncomingMessageItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            when (item.type) {
                MessageType.TEXT.value -> {
                    binding.txtMessage.text = item.content
                }
                MessageType.LOCATION.value -> {
                    binding.txtMessage.hide()
                    binding.mapContainer.show()

                    binding.map.onCreate(null)
                    binding.map.onResume()

                    item.latLng?.let { location ->
                        binding.map.getMapAsync { map ->
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(location.latitude, location.longitude),
                                    15f
                                )
                            )
                            map.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    )
                                )
                            )
                            map.mapType = GoogleMap.MAP_TYPE_NORMAL
                            map.uiSettings.setAllGesturesEnabled(false)
                        }
                    }
                }
            }
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
        return if (messageList[position].uid == Firebase.auth.currentUser?.uid.toString()) {
            OUTGOING
        } else {
            INCOMING
        }
    }

    override fun getItemCount(): Int = messageList.size
}