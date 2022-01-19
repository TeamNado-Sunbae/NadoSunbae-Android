package com.nadosunbae_android.presentation.ui.review.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData.Data.Tag
import com.nadosunbae_android.databinding.ItemListReviewBinding

class ReviewListAdapter(): RecyclerView.Adapter<ReviewListAdapter.ReviewHolder>() {
    // list data
    var dataList = mutableListOf<ResponseReviewListData.Data>()

    class ReviewHolder(private val binding: ItemListReviewBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        // tag info data
        private val tagLink = listOf(
            Pair(Tag(context.getString(R.string.review_curriculum)), binding.tvTagCurriculum ),
            Pair(Tag(context.getString(R.string.review_recommend_lecture)), binding.tvTagRecommendLecture),
            Pair(Tag(context.getString(R.string.review_non_recommend_lecture)), binding.tvTagNonRecommendLecture),
            Pair(Tag(context.getString(R.string.review_career)), binding.tvTagCareer),
            Pair(Tag(context.getString(R.string.review_tip)), binding.tvTagTip)
        )

        fun onBind(data: ResponseReviewListData.Data) {
            binding.previewData = data

            // Apply tag data
            for (t in tagLink) {
                if (data.tagList.contains(t.first))
                    t.second.visibility = View.VISIBLE
                else
                    t.second.visibility = View.INVISIBLE
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        var binding = ItemListReviewBinding.inflate(
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

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setReviewListData(dataList : MutableList<ResponseReviewListData.Data>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

}