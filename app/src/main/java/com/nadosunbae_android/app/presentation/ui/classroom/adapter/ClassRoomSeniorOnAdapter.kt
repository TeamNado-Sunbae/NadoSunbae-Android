package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemQuestionSeniorOnQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.question.DataToFragment
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData

class ClassRoomSeniorOnAdapter(
    var link: DataToFragment, var isClassRoomQuestion : Boolean
) : RecyclerView.Adapter<ClassRoomSeniorOnAdapter.ClassRoomSeniorOnViewHolder>() {
    var onQuestionUserList = mutableListOf<ClassRoomSeniorData.UserSummaryData>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomSeniorOnAdapter.ClassRoomSeniorOnViewHolder {
        val binding = ItemQuestionSeniorOnQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomSeniorOnViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomSeniorOnViewHolder,
        position: Int
    ) {
        holder.onBind(onQuestionUserList[position])
        holder.itemView.setOnClickListener {
            FirebaseAnalyticsUtil.firebaseLog("senior_click","journey","senior_classroom_out")
            link.getSeniorId(onQuestionUserList[position].id)
        }
        if (itemCount > 7 && isClassRoomQuestion) {
            if (holder.layoutPosition > 6) {
                holder.binding.clSeniorMore.visibility = View.VISIBLE
            } else {
                holder.binding.clSeniorMore.visibility = View.GONE
            }
        }

        holder.binding.clSeniorMore.setOnClickListener {
            itemClickListener.onClick(it)
        }
    }

    override fun getItemCount(): Int {
        return onQuestionUserList.size
    }

    interface ItemClickListener {
        fun onClick(view: View)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ClassRoomSeniorOnViewHolder(
        val binding: ItemQuestionSeniorOnQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(onQuestionUserList: ClassRoomSeniorData.UserSummaryData) {
            binding.seniorOn = onQuestionUserList
            binding.executePendingBindings()
        }
    }

    fun setOnQuestionUser(onQuestionUserList: MutableList<ClassRoomSeniorData.UserSummaryData>) {
        this.onQuestionUserList = onQuestionUserList
        notifyDataSetChanged()
    }
}