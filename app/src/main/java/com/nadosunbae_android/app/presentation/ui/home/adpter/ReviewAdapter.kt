package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemHomeReviewBinding
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewDetailActivity
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData

class ReviewAdapter(var userId : Int) :
    androidx.recyclerview.widget.ListAdapter<HomeUnivReviewData, ReviewAdapter.ReviewViewHolder>(
        DiffUtilCallback<HomeUnivReviewData>()
    ) {
    var data = mutableListOf<HomeUnivReviewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemHomeReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.binding.setVariable(BR.reviewDetailData, getItem(position))
        holder.binding.root.setOnClickListener {
            if(userId != MainGlobals.signInData?.userId) {
                userId = MainGlobals.signInData?.userId!!
            }

            val context = holder.itemView.context

            CustomDialog(context).restrictDialog(
                context,
                ReviewGlobals.isReviewed,
                MainGlobals.signInData!!.isUserReported,
                MainGlobals.signInData!!.isReviewInappropriate,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val intent =
                        Intent(holder.itemView.context, ReviewDetailActivity::class.java)
                    val postId = getItem(position).id
                    intent.putExtra("postId", postId)
                    intent.putExtra("userId", userId)
                    ContextCompat.startActivity(holder.itemView.context,intent, null)
                })
        }
    }

    class ReviewViewHolder(
        val binding: ItemHomeReviewBinding,
    ) : RecyclerView.ViewHolder(binding.root)

}