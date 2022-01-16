package com.nadosunbae_android.presentation.ui.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.review.ReviewTagBoxData
import com.nadosunbae_android.databinding.ItemReviewTagBoxBinding

class ReviewTagBoxAdapter : RecyclerView.Adapter<ReviewTagBoxAdapter.ReviewTagBoxHolder>() {
    var dataList = mutableListOf<ReviewTagBoxData>()

    class ReviewTagBoxHolder(private val binding: ItemReviewTagBoxBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ReviewTagBoxData) {
            binding.tagBoxData = data
            binding.ivTagIcon.setImageResource(R.drawable.ic_graphic_diamond)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewTagBoxHolder {
        var binding = ItemReviewTagBoxBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ReviewTagBoxHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewTagBoxHolder, position: Int) = holder.onBind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    fun setReviewTagBoxData(dataList: MutableList<ReviewTagBoxData>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

}