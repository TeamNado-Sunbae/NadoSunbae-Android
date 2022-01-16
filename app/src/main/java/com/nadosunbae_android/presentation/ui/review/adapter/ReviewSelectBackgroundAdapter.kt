package com.nadosunbae_android.presentation.ui.review.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.review.SelectBackgroundBoxData
import com.nadosunbae_android.databinding.ItemListBackgroundBinding

class ReviewSelectBackgroundAdapter : RecyclerView.Adapter<ReviewSelectBackgroundAdapter.ReviewSelectBackgroundHolder>() {
    var dataList = mutableListOf<SelectBackgroundBoxData>()
    private var mSelectedPos: Int = -1

    class ReviewSelectBackgroundHolder(val binding: ItemListBackgroundBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SelectBackgroundBoxData) {
            binding.imageUrl = data.imageUrl
            if (data.isSelected) {
                binding.clSelectedBackground.visibility = View.VISIBLE
            }
            else {
                binding.clSelectedBackground.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewSelectBackgroundHolder {
        var binding = ItemListBackgroundBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ReviewSelectBackgroundHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewSelectBackgroundHolder,
                                  @SuppressLint("RecyclerView") position: Int) {
        holder.onBind(dataList[position])

        holder.itemView.setOnClickListener {

            when (mSelectedPos) {
                // 새로 선택할 때
                NOT_SELECTED -> {
                    mSelectedPos = position
                    dataList[position].isSelected = true
                }

                // 선택된 배경을 다시 선택했을 경우 -> 선택 해제
                position -> {
                    mSelectedPos = NOT_SELECTED
                    dataList[position].isSelected = false
                }

                // 다른 배경을 선택했을 경우 -> 선택 변경
                else -> {
                    dataList[mSelectedPos].isSelected = false
                    mSelectedPos = position
                    dataList[position].isSelected = true
                }
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = dataList.size

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    companion object {
        const val NOT_SELECTED = -1
    }

}