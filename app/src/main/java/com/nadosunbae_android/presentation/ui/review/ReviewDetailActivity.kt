package com.nadosunbae_android.presentation.ui.review

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.review.ReviewTagBoxData
import com.nadosunbae_android.databinding.ActivityReviewDetailBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewTagBoxAdapter
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewDetailViewModel


class ReviewDetailActivity :
    BaseActivity<ActivityReviewDetailBinding>(R.layout.activity_review_detail) {

    private lateinit var reviewTagBoxAdapter: ReviewTagBoxAdapter

    private val reviewDetailViewModel: ReviewDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ReviewDetailViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTagBoxAdapter()
        initBinding()
        setClickListener()
        observeBackground()
        setTestData()

    }

    private fun initTagBoxAdapter() {
        reviewTagBoxAdapter = ReviewTagBoxAdapter(this)
        binding.rvReviewDetail.adapter = reviewTagBoxAdapter
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.reviewDetailViewModel = reviewDetailViewModel
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun observeBackground() {

        reviewDetailViewModel.urlBackground.observe(this) {

        }
    }

    private fun setTestData() {

        val sampleList = mutableListOf<ReviewTagBoxData>(
            ReviewTagBoxData("장단점", "example content.. 난 자유롭고 싶어 지금 전투력 수치 111퍼 가나다라마바사아자차카타파하 hello world 다른 사람과 만났을 때 하는 인사말. 누구나 한번쯤은 들었을만한 인사, 한자로는 安(편할 안)에 寧(편안할 녕), \"무탈하시오?\"라고 풀이할 수 있다. 즉 탈없이 무사하냐는 뜻이며 \"오랜만에 봤는데 건강은 괜찮으시오?\" \"아무일 없으시죠?\" 등 상대방의 건강을 챙기고 걱정해주는 인사말. 이는 보릿고개, 호환, 외적의 침입등 살기 각박한 조상님들의 삶의 모습이 비춰진 인사말이라고 한다"),
            ReviewTagBoxData("추천 수업", "example content.. 난 자유롭고 싶어 지금 전투력 수치 111퍼 가나다라마바사아자차카타파하 hello world"),
            ReviewTagBoxData("비추 수업", "example content.. 난 자유롭고 싶어 지금 전투력 수치 111퍼 가나다라마바사아자차카타파하 hello world다른 사람과 만났을 때 하는 인사말. 누구나 한번쯤은 들었을만한 인사, 한자로는 安(편할 안)에 寧(편안할 녕), \"무탈하시오?\"라고 풀이할 수 있다. 즉 탈없이 무사하냐는 뜻이며 \"오랜만에 봤는데 건강은 괜찮으시오?\" \"아무일 없으시죠?\" 등 상대방의 건강을 챙기고 걱정해주는 인사말. 이는 보릿고개, 호환, 외적의 침입등 살기 각박한 조상님들의 삶의 모습이 비춰진 인사말이라고 한다"),
            ReviewTagBoxData("꿀팁", "example content.. 난 자유롭고 싶어 지금 전투력 수치 111퍼 가나다라마바사아자차카타파하 hello world"),
        )
        reviewTagBoxAdapter.setReviewTagBoxData(sampleList)

        reviewDetailViewModel.setBackgroundUrl("https://cdn.zeplin.io/61d5107362df6f18539e470d/assets/166af9e7-07e2-4f74-bb1f-e0a33151ef5e.png")
    }

}