package com.nadosunbae_android.presentation.ui.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.databinding.ItemNotificationBinding
import com.nadosunbae_android.databinding.ItemQuestionAllBinding
import com.nadosunbae_android.presentation.ui.notification.NotificationFragment

class NotificationAdapter(
    var link : NotificationFragment.DataToFragment
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    var notifiCationList = mutableListOf<ResponseNotificationListData.Data.Notification>()

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
        holder.onBind(notifiCationList[position])
        holder.binding.imgNotificationDelete.setOnClickListener {
            link.getNotificationId(notifiCationList[position].notificationId)
        }

    }

    override fun getItemCount(): Int = notifiCationList.size

    inner class NotificationViewHolder(
        val binding : ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(notificationList : ResponseNotificationListData.Data.Notification){
            binding.apply {
                notification = notificationList
                executePendingBindings()
            }
        }
    }

    fun setNotification(notificationList : MutableList<ResponseNotificationListData.Data.Notification>){
        this.notifiCationList = notificationList
        notifyDataSetChanged()

    }
}