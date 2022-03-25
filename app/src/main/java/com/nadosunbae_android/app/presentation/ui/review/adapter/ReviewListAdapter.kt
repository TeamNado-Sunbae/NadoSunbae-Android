package com.nadosunbae_android.app.presentation.ui.review.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemListReviewBinding
import com.nadosunbae_android.domain.model.review.ReviewPreviewData
import timber.log.Timber

class ReviewListAdapter(): RecyclerView.Adapter<ReviewListAdapter.ReviewHolder>() {
    // list data
    var dataList = mutableListOf<ReviewPreviewData>()

    class ReviewHolder(private val binding: ItemListReviewBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        // tag info data
        private val tagLink = listOf(
            Pair(context.getString(R.string.review_curriculum), binding.tvTagCurriculum ),
            Pair(context.getString(R.string.review_recommend_lecture), binding.tvTagRecommendLecture),
            Pair(context.getString(R.string.review_non_recommend_lecture), binding.tvTagNonRecommendLecture),
            Pair(context.getString(R.string.review_career), binding.tvTagCareer),
            Pair(context.getString(R.string.review_tip), binding.tvTagTip)
        )

        fun onBind(data: ReviewPreviewData) {
            binding.previewData = data

            // onBind 시에 Flexbox.LayoutParam에 저장되어 있는 isWrapBefore 초기화
            val prevLayoutParams =
                binding.tvFirstMajor.layoutParams as FlexboxLayout.LayoutParams
            binding.tvFirstMajor.layoutParams = prevLayoutParams.apply {
                isWrapBefore = false
            }

            // Flexbox 레이아웃에서 자동개행이 되었을 때, 닉네임 뒤에 자동 개행을 붙임
            binding.flReviewWriterInfo.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    // 높이가 다를 경우 = 개행된 상태
                    if (binding.flReviewWriterInfo.height != binding.tvNickname.height) {
                        Timber.d("InsertNewLine: fl=${binding.flReviewWriterInfo.height} nickname=${binding.tvNickname.height}")

                        // 닉네임 뒤에 개행
                        binding.tvFirstMajor.layoutParams = prevLayoutParams.apply {
                            prevLayoutParams.isWrapBefore = true
                        }

                    } else {
                        binding.tvFirstMajor.layoutParams = prevLayoutParams.apply {
                            prevLayoutParams.isWrapBefore = false
                        }
                    }
                    binding.flReviewWriterInfo.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })


            // Apply tag data
            for (t in tagLink) {
                if (data.tagList.contains(t.first))
                    t.second.visibility = View.VISIBLE
                else
                    t.second.visibility = View.GONE
            }

            // second major visibility
            if (data.secondMajorName == NOT_ENTERED) {
                binding.viewLineVertical.visibility = View.INVISIBLE
                binding.tvSecondMajor.visibility = View.INVISIBLE
            }
            else {
                binding.viewLineVertical.visibility = View.VISIBLE
                binding.tvSecondMajor.visibility = View.VISIBLE
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding = ItemListReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ReviewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.onBind(dataList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun isEmpty(): Boolean = dataList.isEmpty()

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setReviewListData(dataList : MutableList<ReviewPreviewData>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    companion object {
        const val NOT_ENTERED = "미진입"
    }

}