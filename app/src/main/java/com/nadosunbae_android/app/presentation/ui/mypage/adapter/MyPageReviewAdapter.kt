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
import com.nadosunbae_android.app.databinding.ItemMypageReviewBinding
import com.nadosunbae_android.app.presentation.ui.classroom.InformationDetailActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.review.ReviewDetailActivity
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.review.ReviewWriteActivity
import com.nadosunbae_android.app.presentation.ui.review.ReviewWriteActivity.Companion.MODE_NEW
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData
import com.nadosunbae_android.domain.model.mypage.MyPageReviewData

class MyPageReviewAdapter(var userId : Int):
    RecyclerView.Adapter<MyPageReviewAdapter.MyPageReviewViewHolder>() {

    var myPageReviewData = mutableListOf<MyPageReviewData.Data.ReviewPost>()


    class MyPageReviewViewHolder(private val binding: ItemMypageReviewBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        // tag info data
        private val tagLink = listOf(
            Pair(context.getString(R.string.review_curriculum), binding.tvTagCurriculum ),
            Pair(context.getString(R.string.review_recommend_lecture), binding.tvTagRecommendLecture),
            Pair(context.getString(R.string.review_non_recommend_lecture), binding.tvTagNonRecommendLecture),
            Pair(context.getString(R.string.review_career), binding.tvTagCareer),
            Pair(context.getString(R.string.review_tip), binding.tvTagTip)
        )

        fun onBind(data: MyPageReviewData.Data.ReviewPost) {
            binding.apply {
                for (t in tagLink) {
                    if (data.tagList.contains(MyPageReviewData.Data.ReviewPost.Tag(t.first)))
                        t.second.visibility = View.VISIBLE
                    else
                        t.second.visibility = View.GONE
                }

                reviewData = data
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageReviewViewHolder {
        var binding = ItemMypageReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return MyPageReviewViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(
        holder: MyPageReviewAdapter.MyPageReviewViewHolder,
        position: Int
    ) {
        holder.onBind(myPageReviewData[position])
        holder.itemView.setOnClickListener {
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
                    val writeIntent = Intent(context, ReviewWriteActivity::class.java)
                    writeIntent.apply {
                        putExtra("mode", MODE_NEW)
                    }
                    context.startActivity(writeIntent)
                })
        }
    }

    override fun getItemCount(): Int = myPageReviewData.size

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setReviewListData(dataList : MutableList<MyPageReviewData.Data.ReviewPost>){
        this.myPageReviewData = dataList
        notifyDataSetChanged()
    }


}