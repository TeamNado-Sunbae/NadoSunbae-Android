package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.home.adpter.BannerListAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.CommunityAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.QuestionAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.ReviewAdapter
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private var reviewList = mutableListOf<HomeUnivReviewData>()
    private val homeViewModel : HomeViewModel by viewModels()
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var bannerAdapter : BannerListAdapter
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setReviewAdapter()
        setQuestionAdapter()
        setCommunityAdapter()
        naviControl()
        setBanner()
    }

    private fun setBanner() {
//        bannerAdapter = BannerListAdapter(homeViewModel.BannerData.subList(1, 1))
//        binding.vpHomeBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                if (position == 0) {
//                    Timber.d("TEST1")
//                }
//                else if (position == 1) {
//                    Timber.d("TEST2")
//                }
//                else if (position == 2){
//                    Timber.d("TEST3")
//                }
//            }
//        })
    }

    //홈 뷰 리뷰 리사이클러뷰 연결
    private fun setReviewAdapter() {
        //TODO : 후기 미작성 유저 알럿 확인 필요
        Timber.e("UserIdTest1: ${mainViewModel.userId.value}")
        Timber.e("UserUnivIdTest1 : ${mainViewModel.univId.value}")
        reviewAdapter = ReviewAdapter(mainViewModel.userId.value ?: 0)
        binding.rvHomeReview.adapter = reviewAdapter
        homeViewModel.getReviewDetail(mainViewModel.univId.value ?: 0)

        //홈 뷰에서는 item 5개만 띄우기
        homeViewModel.reviewDetail.observe(viewLifecycleOwner) {
            for (i in 0 until 5) {
                reviewList.add(HomeUnivReviewData(it[i].createdAt,it[i].id, it[i].majorName, it[i].oneLineReview, it[i].tagList, it[i].isLiked, it[i].likeCount))
            }
            (binding.rvHomeReview.adapter as ReviewAdapter).submitList(reviewList)
        }
    }

    //홈 뷰 질문 리사이클러뷰 연결
    private fun setQuestionAdapter() {
        binding.rvHomeQuestion.adapter = QuestionAdapter()
        (binding.rvHomeQuestion.adapter as QuestionAdapter).submitList(homeViewModel.questionData)
    }

    //홈 뷰 커뮤니티 리사이클러뷰 연결
    private fun setCommunityAdapter() {
        binding.rvHomeCommunity.adapter = CommunityAdapter()
        (binding.rvHomeCommunity.adapter as CommunityAdapter).submitList(homeViewModel.communityData)
    }

    private fun naviControl() {
        binding.tvHomeReviewMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeReviewFragment)
        }

        binding.tvHomeNewQuestionMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeQuestionFragment)
        }

        binding.tvHomeRankingMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeRankingFragment)
        }
    }

}