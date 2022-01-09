package com.nadosunbae_andorid.presentation.ui.review

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_andorid.data.model.review.PreviewData
import com.nadosunbae_andorid.databinding.ItemListReviewBinding

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {
    val dataList = mutableListOf<PreviewData>()

    class ReviewHolder(private val binding: ItemListReviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PreviewData) {
            with(binding) {

            }
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}