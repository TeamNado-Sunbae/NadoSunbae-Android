package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemMypageLikeReviewBinding
import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData

class MyPageLikeReviewAdapter():
    RecyclerView.Adapter<MyPageLikeReviewAdapter.MyPageLikeReviewViewHolder>() {

    var myPageLikeReviewData = mutableListOf<MyPageLikeReviewData.Data.LikePost>()

    class MyPageLikeReviewViewHolder(private val binding: ItemMypageLikeReviewBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        // tag info data
        private val tagLink = listOf(
            Pair(context.getString(R.string.review_curriculum), binding.tvTagCurriculum ),
            Pair(context.getString(R.string.review_recommend_lecture), binding.tvTagRecommendLecture),
            Pair(context.getString(R.string.review_non_recommend_lecture), binding.tvTagNonRecommendLecture),
            Pair(context.getString(R.string.review_career), binding.tvTagCareer),
            Pair(context.getString(R.string.review_tip), binding.tvTagTip)
        )

        fun onBind(data: MyPageLikeReviewData.Data.LikePost) {
            binding.apply {
                for (t in tagLink) {
                    if (data.tagList.contains(MyPageLikeReviewData.Data.LikePost.Tag(t.first)))
                        t.second.visibility = View.VISIBLE
                    else
                        t.second.visibility = View.GONE
                }

                myPageLikeReview = data
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageLikeReviewViewHolder {
        var binding = ItemMypageLikeReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return MyPageLikeReviewViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(
        holder: MyPageLikeReviewViewHolder,
        position: Int
    ) {
        holder.onBind(myPageLikeReviewData[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = myPageLikeReviewData.size

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setReviewListData(dataList : MutableList<MyPageLikeReviewData.Data.LikePost>){
        this.myPageLikeReviewData = dataList
        notifyDataSetChanged()
    }

}