package com.nadosunbae_android.presentation.ui.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.databinding.ItemNotificationBinding
import com.nadosunbae_android.databinding.ItemQuestionAllBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    val exampleData = mutableListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NotificationAdapter.NotificationViewHolder,
        
        position: Int
    ) {

    }

    override fun getItemCount(): Int = exampleData.size

    inner class NotificationViewHolder(
        val binding : ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root){


    }
}