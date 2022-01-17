package com.nadosunbae_android.presentation.ui.review.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.review.ReviewTagBoxData
import com.nadosunbae_android.databinding.ItemReviewTagBoxBinding

class ReviewTagBoxAdapter(private val context: Context) : RecyclerView.Adapter<ReviewTagBoxAdapter.ReviewTagBoxHolder>() {
    var dataList = mutableListOf<ReviewTagBoxData>()

    class ReviewTagBoxHolder(private val binding: ItemReviewTagBoxBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ReviewTagBoxData) {
            binding.tagBoxData = data
            binding.executePendingBindings()

            // 라벨 아이콘 이미지 소스
            var imageResource = 0
            var marginStart = 0
            var marginTop = 0

            when (data.labelName) {
                context.getString(R.string.review_pros_cons) -> {
                    imageResource = R.drawable.ic_graphic_cube
                    marginStart = 9
                    marginTop = 15
                }
                context.getString(R.string.review_curriculum) -> {
                    imageResource = R.drawable.ic_graphic_pencil
                    marginStart = 11
                    marginTop = 29
                }
                context.getString(R.string.review_recommend_lecture) -> {
                    imageResource = R.drawable.ic_graphic_diamond
                    marginStart = 13
                    marginTop = 18
                }
                context.getString(R.string.review_non_recommend_lecture) -> {
                    imageResource = R.drawable.ic_graphic_bomb
                    marginStart = 22
                    marginTop = 21
                }
                context.getString(R.string.review_career) -> {
                    imageResource = R.drawable.ic_graphic_compass
                    marginStart = 10
                    marginTop = 24
                }
                context.getString(R.string.review_tip) -> {
                    imageResource = R.drawable.ic_graphic_honey
                    marginStart = 11
                    marginTop = 14
                }
            }

            binding.bindMarginStart = marginStart
            binding.bindMarginTop = marginTop
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

    fun setReviewTagBoxData(dataList: MutableList<ReviewTagBoxData>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

}