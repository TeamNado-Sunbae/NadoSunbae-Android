package com.nadosunbae_android.app.presentation.ui.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemNotificationBinding
import com.nadosunbae_android.domain.model.notification.NotificationListData
import com.nadosunbae_android.app.presentation.ui.notification.NotificationFragment
import com.nadosunbae_android.app.util.CustomDialog

class NotificationAdapter(
    var link: NotificationFragment.DataToFragment
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
        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            if (notifiCationList[position].content == context.getString(R.string.classroom_content_delete)
                || notifiCationList[position].content == context.getString(R.string.classroom_post_delete)) {
                CustomDialog(holder.itemView.context).deleteNotificationDialog()
            } else {
                link.getReadNotification(notifiCationList[position].notificationId)
                link.getNotificationMove(
                    notifiCationList[position].postId,
                    notifiCationList[position].notificationTypeId
                )
            }


        }
        holder.binding.imgNotificationDelete.setOnClickListener {
            link.getNotificationId(notifiCationList[position].notificationId)
        }

    }

    override fun getItemCount(): Int = notifiCationList.size

    inner class NotificationViewHolder(
        val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(notificationList: NotificationListData) {
            binding.apply {
                notification = notificationList
                executePendingBindings()
            }
        }
    }

    fun setNotification(notificationList: MutableList<NotificationListData>) {
        this.notifiCationList = notificationList
        notifyDataSetChanged()

    }
}