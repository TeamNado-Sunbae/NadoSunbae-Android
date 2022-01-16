package com.nadosunbae_andorid.presentation.ui.review.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.data.model.response.review.PreviewData
import com.nadosunbae_andorid.databinding.ItemListBackgroundBinding
import com.nadosunbae_andorid.databinding.ItemListReviewBinding

class ReviewSelectBackgroundAdapter : RecyclerView.Adapter<ReviewSelectBackgroundAdapter.ReviewSelectBackgroundHolder>() {
    var dataList = mutableListOf<String>()

    class ReviewSelectBackgroundHolder(private val binding: ItemListBackgroundBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            binding.urlBackground = data
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

    override fun onBindViewHolder(holder: ReviewSelectBackgroundHolder, position: Int) {
        holder.onBind(dataList[position])

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = dataList.size

}