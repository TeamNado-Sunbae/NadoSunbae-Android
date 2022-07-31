package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemHomeReviewBinding
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.home.HomeReviewData

class ReviewAdapter :  androidx.recyclerview.widget.ListAdapter<HomeReviewData, ReviewAdapter.ReviewViewHolder>(
    DiffUtilCallback<HomeReviewData>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemHomeReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.binding.setVariable(BR.Review, getItem(position))
    }


    class ReviewViewHolder(
        val binding: ItemHomeReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}