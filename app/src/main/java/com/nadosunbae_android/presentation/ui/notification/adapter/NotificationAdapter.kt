package com.nadosunbae_android.presentation.ui.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.model.response.notification.ResponseNotificationListData
import com.nadosunbae_android.databinding.ItemNotificationBinding
import com.nadosunbae_android.model.notification.NotificationListData
import com.nadosunbae_android.presentation.ui.notification.NotificationFragment

class NotificationAdapter(
    var link : NotificationFragment.DataToFragment
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    var notifiCationList = mutableListOf<NotificationListData>()

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
        holder.itemView.setOnClickListener {
            link.getReadNotification(notifiCationList[position].notificationId)
            link.getNotificationMove(notifiCationList[position].postId,
            notifiCationList[position].notificationType)
        }
        holder.binding.imgNotificationDelete.setOnClickListener {
            link.getNotificationId(notifiCationList[position].notificationId)
        }

    }

    override fun getItemCount(): Int = notifiCationList.size

    inner class NotificationViewHolder(
        val binding : ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(notificationList : NotificationListData){
            binding.apply {
                notification = notificationList
                executePendingBindings()
            }
        }
    }

    fun setNotification(notificationList : MutableList<NotificationListData>){
        this.notifiCationList = notificationList
        notifyDataSetChanged()

    }
}