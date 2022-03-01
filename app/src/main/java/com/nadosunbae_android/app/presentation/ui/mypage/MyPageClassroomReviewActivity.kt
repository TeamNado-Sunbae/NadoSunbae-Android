package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageClassroomReviewBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewDetailActivity
import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData
import com.nadosunbae_android.domain.model.mypage.MyPageReviewData
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyPageClassroomReviewActivity : BaseActivity<ActivityMyPageClassroomReviewBinding>(R.layout.activity_my_page_classroom_review) {

    private val myPageViewModel: MyPageViewModel by viewModel()
    private lateinit var myPageReviewAdapter: MyPageReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLoadingEnd()
        initReviewListAdapter()
        backBtn()
        setClickListener()
        initNickName()

    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }


    private fun initNickName(){
        binding.textMypageReviewNickname.setText(intent.getStringExtra("userNickName"))
    }

    private fun backBtn() {
        binding.imgMypageReviewTitle.setOnClickListener {
            finish()
        }
    }

    private fun initReviewListAdapter() {
        showLoading()
        myPageViewModel.getMyPageReview(intent.getIntExtra("userId", 0))
        myPageReviewAdapter = MyPageReviewAdapter()
        binding.rvMypageReview.adapter = myPageReviewAdapter
        myPageViewModel.reviewList.observe(this) {
            myPageReviewAdapter.setReviewListData((it.data.reviewPostList) as MutableList<MyPageReviewData.Data.ReviewPost>)
        }
    }

    private fun setClickListener() {
        showLoading()
        // RecyclerView ItemClickListener
        myPageReviewAdapter.setItemClickListener(
            object : MyPageReviewAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {

                    val reviewData = myPageViewModel.reviewList.value

                    // null check
                    if (reviewData != null) {
                        // postId Intent로 전달 (후기 상세보기 이동)
                        val intent =
                            Intent(this@MyPageClassroomReviewActivity, ReviewDetailActivity::class.java)
                        val postId = reviewData.data.reviewPostList[position].postId
                        intent.putExtra("postId", postId)
                        intent.putExtra("userId", intent.getIntExtra("userId", 0))
                        startActivity(intent)
                    }


                }

            }
        )
    }
}