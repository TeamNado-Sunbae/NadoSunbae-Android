package com.nadosunbae_android.app.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.home.adpter.RankingDetailAdapter
import com.nadosunbae_android.domain.model.home.*
import com.nadosunbae_android.domain.repository.community.CommunityRepository
import com.nadosunbae_android.domain.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>(false)

    var userId = MutableLiveData<Int>()

    private val _reviewDetail = MutableLiveData<List<HomeUnivReviewData>>()
    val reviewDetail: LiveData<List<HomeUnivReviewData>>
        get() = _reviewDetail

    private val _rankingData = MutableLiveData<List<HomeRankingData>>()
    val rankingData: LiveData<List<HomeRankingData>>
        get() = _rankingData

    val questionData = listOf<HomeQuestionData>(
        HomeQuestionData(
            "질문자닉네임",
            "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...",
            "21/12/23"
        ),
        HomeQuestionData(
            "질문자닉네임",
            "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...",
            "21/12/23"
        ),
        HomeQuestionData(
            "질문자닉네임",
            "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...",
            "21/12/23"
        ),
        HomeQuestionData(
            "질문자닉네임",
            "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...",
            "21/12/23"
        ),
        HomeQuestionData(
            "질문자닉네임",
            "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...",
            "21/12/23"
        ),
        HomeQuestionData(
            "질문자닉네임",
            "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...",
            "21/12/23"
        )
    )


    val BannerData = listOf(
        Banner("1", "https://upload3.inven.co.kr/upload/2022/01/28/bbs/i13648532370.jpg"),
        Banner("2", "https://upload3.inven.co.kr/upload/2022/01/28/bbs/i13648532370.jpg"),
        Banner("3", "https://upload3.inven.co.kr/upload/2022/01/28/bbs/i13648532370.jpg")

    )

    fun getReviewDetail(university: Int) {
        viewModelScope.launch {
            kotlin.runCatching { homeRepository.getUnivReview(university) }
                .onSuccess {
                    _reviewDetail.value = it
                    Timber.d("학교별 리뷰 : 서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("학교별 리뷰 : 서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    fun getHomeRanking(university: Int) {
        viewModelScope.launch {
            kotlin.runCatching { homeRepository.getHomeRanking(university) }
                .onSuccess {
                    _rankingData.value = it
                    Timber.d("선배 랭킹 : 서버통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("선배 랭킹 : 서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }
}