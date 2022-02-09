package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.databinding.ItemInformationDetailBinding
import com.nadosunbae_android.data.model.classroom.InfoDetailData

class ClassRoomInfoDetailAdapter : RecyclerView.Adapter<ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder>() {
    var infoDetailData = mutableListOf<InfoDetailData.Comment>()

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
        if(infoDetailData[position].secondMajorName == "미진입"){
            holder.binding.textInformationDetailContentSecondMajorStart.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = infoDetailData.size

    inner class ClassRoomInfoDetailViewHolder(
        val binding : ItemInformationDetailBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(infoDetailData : InfoDetailData.Comment){
            binding.apply {
                infoDetailListData = infoDetailData
                executePendingBindings()
            }
        }
    }

    fun setInfoDetail(infoDetailData: MutableList<InfoDetailData.Comment>){
        this.infoDetailData = infoDetailData
        notifyDataSetChanged()
    }
}