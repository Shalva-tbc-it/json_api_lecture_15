package com.example.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.message.data_model.ChatItem
import com.example.message.data_model.MessageType
import com.example.message.databinding.RecyclerChatItemBinding

class ChatRecyclerAdapter : ListAdapter<ChatItem, ChatRecyclerAdapter.ChatViewHolder>(object :
    DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            RecyclerChatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind()
    }

    inner class ChatViewHolder(private val binding: RecyclerChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            val item = currentList[adapterPosition]
            Glide
                .with(root.context)
                .load(item.image)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgProfile)

            tvFullName.text = item.owner
            tvMessage.text = item.lastMessage
            when (item.lastMessageType) {
                MessageType.FILE.toString() -> {
                    imgMessageType.visibility = View.VISIBLE
                    imgMessageType.setImageResource(R.drawable.attachment)
                }
                MessageType.VOICE.toString() -> {
                    imgMessageType.visibility = View.VISIBLE
                    imgMessageType.setImageResource(R.drawable.recorder)
                }
                MessageType.VOICE.toString() -> {
                    imgMessageType.visibility = View.GONE
                }
            }
            tvTime.text = item.lastActive
            if (item.unreadMessages > 0) {
                tvUncheckMessage.visibility = View.VISIBLE
                tvUncheckMessage.text = item.unreadMessages.toString()
            }else {
                tvUncheckMessage.visibility = View.GONE
            }
        }

    }

}