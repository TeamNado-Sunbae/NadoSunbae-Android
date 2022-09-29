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
    //알림 읽기
    private var onItemCLickListener: ((Int, Int, Int) -> Unit)? =null
    private var onDeleteClickListener: (Int) -> Unit? = {}

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
            if (getItem(holder.absoluteAdapterPosition).content == context.getString(R.string.classroom_content_delete)
                || getItem(position).content == context.getString(R.string.classroom_post_delete)
            ) {
                CustomDialog(context).deleteNotificationDialog()
            } else {
                onItemCLickListener?.let {
                    it(getItem(holder.absoluteAdapterPosition).notificationId,
                    getItem(holder.absoluteAdapterPosition).postId,
                    getItem(holder.absoluteAdapterPosition).notificationTypeId)
                }
            }
        }

        holder.binding.imgNotificationDelete.setOnClickListener {
            onDeleteClickListener.let {
                it(getItem(holder.absoluteAdapterPosition).notificationId)
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

    //알림 읽기
    fun setItemClickListener(listener: (Int, Int, Int) -> Unit) {
        this.onItemCLickListener = listener
    }

    //알림 삭제
    fun setDeleteClickListener(listener: (Int) -> Unit) {
        this.onDeleteClickListener = listener
    }
}