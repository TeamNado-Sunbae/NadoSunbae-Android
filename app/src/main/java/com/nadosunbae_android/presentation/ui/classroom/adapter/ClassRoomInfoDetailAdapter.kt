package com.nadosunbae_android.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.classroom.ResponseInfoDetailData
import com.nadosunbae_android.databinding.ItemInformationDetailBinding

class ClassRoomInfoDetailAdapter : RecyclerView.Adapter<ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder>() {
    var infoDetailData = mutableListOf<ResponseInfoDetailData.Data.Comment>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder {
        val binding = ItemInformationDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomInfoDetailViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder,
        position: Int
    ) {
        holder.onBind(infoDetailData[position])
    }

    override fun getItemCount(): Int = infoDetailData.size

    inner class ClassRoomInfoDetailViewHolder(
        val binding : ItemInformationDetailBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(infoDetailData : ResponseInfoDetailData.Data.Comment){
            binding.apply {
                infoDetailListData = infoDetailData
                executePendingBindings()
            }
        }
    }

    fun setInfoDetail(infoDetailData: MutableList<ResponseInfoDetailData.Data.Comment>){
        this.infoDetailData = infoDetailData
        notifyDataSetChanged()
    }
}