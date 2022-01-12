package com.nadosunbae_andorid.presentation.ui.review.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_andorid.data.model.review.PreviewData
import com.nadosunbae_andorid.databinding.ItemListReviewBinding

class ReviewListAdapter: RecyclerView.Adapter<ReviewListAdapter.ReviewHolder>() {
    val dataList = mutableListOf<PreviewData>()

    class ReviewHolder(private val binding: ItemListReviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PreviewData) {
            binding.previewData = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        var binding = ItemListReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ReviewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.onBind(dataList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}