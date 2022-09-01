package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemHomeReviewDetailBinding
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData

class ReviewDetailAdapter :
    androidx.recyclerview.widget.ListAdapter<HomeUnivReviewData, ReviewDetailAdapter.ReviewDetailViewHolder>(
        DiffUtilCallback<HomeUnivReviewData>()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewDetailViewHolder {
        val binding = ItemHomeReviewDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewDetailViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ReviewDetailViewHolder, position: Int) {
        holder.binding.setVariable(BR.reviewDetailData, getItem(position))
        holder.onBind(getItem(position))
    }

    class ReviewDetailViewHolder(
        val binding: ItemHomeReviewDetailBinding, private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        private val tagLink = listOf(
            Pair(context.getString(R.string.review_curriculum), binding.tvTagCurriculum),
            Pair(
                context.getString(R.string.review_recommend_lecture),
                binding.tvTagRecommendLecture
            ),
            Pair(
                context.getString(R.string.review_non_recommend_lecture),
                binding.tvTagNonRecommendLecture
            ),
            Pair(context.getString(R.string.review_career), binding.tvTagCareer),
            Pair(context.getString(R.string.review_tip), binding.tvTagTip)

        )

        fun onBind(data: HomeUnivReviewData) {
            for (t in tagLink) {
                if (data.tagList.contains(t.first))
                    t.second.visibility = View.VISIBLE
                else
                    t.second.visibility = View.GONE
            }
        }
    }
}