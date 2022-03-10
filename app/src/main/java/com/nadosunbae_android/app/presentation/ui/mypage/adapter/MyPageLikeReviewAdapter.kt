package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemMypageLikeReviewBinding
import com.nadosunbae_android.app.presentation.ui.review.ReviewDetailActivity
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.review.ReviewWriteActivity
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData

class MyPageLikeReviewAdapter(var userId : Int):
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

            val context = holder.itemView.context

            if (ReviewGlobals.isReviewed) {
                val intent = Intent(context, ReviewDetailActivity::class.java)
                val postId = myPageLikeReviewData[position].postId
                intent.putExtra("postId", postId)
                intent.putExtra("userId", userId)
                ContextCompat.startActivity(holder.itemView.context,intent, null)
            }
            else {
                // 후기 미작성시 알럿 띄우기
                CustomDialog(context)
                    .genericDialog(
                        CustomDialog.DialogData(
                        context.getString(R.string.alert_no_review_title),
                        context.getString(R.string.alert_no_review_complete),
                        context.getString(R.string.alert_no_review_cancel)
                    ),
                        complete = {
                            val writeIntent = Intent(context, ReviewWriteActivity::class.java)
                            writeIntent.apply {
                                putExtra("mode", ReviewWriteActivity.MODE_NEW)
                            }
                            context.startActivity(writeIntent)
                        },
                        cancel = {

                        }
                    )
            }


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