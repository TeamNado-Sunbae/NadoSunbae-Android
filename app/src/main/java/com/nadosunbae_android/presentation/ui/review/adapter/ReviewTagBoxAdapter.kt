package com.nadosunbae_android.presentation.ui.review.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.review.ResponseReviewDetailData
import com.nadosunbae_android.data.model.ui.ReviewTagBoxData
import com.nadosunbae_android.databinding.ItemReviewTagBoxBinding

class ReviewTagBoxAdapter(private val context: Context) : RecyclerView.Adapter<ReviewTagBoxAdapter.ReviewTagBoxHolder>() {
    var dataList = listOf<ResponseReviewDetailData.Data.Post.Content>()

    class ReviewTagBoxHolder(private val binding: ItemReviewTagBoxBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseReviewDetailData.Data.Post.Content) {
            binding.contentBox = data
            binding.executePendingBindings()

            // 라벨 아이콘 이미지 소스
            var imageResource = 0

            when (data.title) {
                context.getString(R.string.review_pros_cons) -> imageResource = R.drawable.ic_graphic_cube
                context.getString(R.string.review_curriculum) -> imageResource = R.drawable.ic_graphic_pencil
                context.getString(R.string.review_recommend_lecture) -> imageResource = R.drawable.ic_graphic_diamond
                context.getString(R.string.review_non_recommend_lecture) -> imageResource = R.drawable.ic_graphic_bomb
                context.getString(R.string.review_career) -> imageResource = R.drawable.ic_graphic_compass
                context.getString(R.string.review_tip) -> imageResource = R.drawable.ic_graphic_honey
            }

            binding.ivTagIcon.setImageResource(imageResource)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewTagBoxHolder {
        var binding = ItemReviewTagBoxBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ReviewTagBoxHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ReviewTagBoxHolder, position: Int) = holder.onBind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    fun setReviewTagBoxData(dataList: List<ResponseReviewDetailData.Data.Post.Content>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

}