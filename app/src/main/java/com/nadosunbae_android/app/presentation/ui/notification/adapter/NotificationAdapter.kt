package com.nadosunbae_android.app.presentation.ui.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemNotificationBinding
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.notification.NotificationData

class NotificationAdapter(
) : ListAdapter<NotificationData, NotificationAdapter.NotificationViewHolder>(
    DiffUtilCallback<NotificationData>()
) {

    private var onItemCLickListener: (Int) -> Unit? = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NotificationViewHolder,
        position: Int
    ) {
        holder.onBind(getItem(position))
        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            if (getItem(position).content == context.getString(R.string.classroom_content_delete)
                || getItem(position).content == context.getString(R.string.classroom_post_delete)
            ) {
                CustomDialog(context).deleteNotificationDialog()
            } else {
                onItemCLickListener.let {
                    it(getItem(position).notificationId)
                }
            }
        }
    }


    class NotificationViewHolder(
        val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(notificationList: NotificationData) {
            binding.apply {
                notification = notificationList
                executePendingBindings()
            }
        }
    }

    fun setItemClickListener(listener: (Int) -> Unit) {
        this.onItemCLickListener = listener
    }
}