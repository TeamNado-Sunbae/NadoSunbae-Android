package com.nadosunbae_android.app.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.app.AppBannerData
import com.nadosunbae_android.domain.model.home.HomeRankingData
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData
import com.nadosunbae_android.domain.repository.app.AppRepository
import com.nadosunbae_android.domain.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val appRepository: AppRepository
) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>(false)

    var userId = MutableLiveData<Int>()

    private val _reviewDetail = MutableLiveData<List<HomeUnivReviewData>>()
    val reviewDetail: LiveData<List<HomeUnivReviewData>>
        get() = _reviewDetail

    private val _rankingData = MutableLiveData<List<HomeRankingData>>()
    val rankingData: LiveData<List<HomeRankingData>>
        get() = _rankingData

    private val _bannerData = MutableLiveData<List<AppBannerData>>()
    val bannerData: LiveData<List<AppBannerData>>
        get() = _bannerData

    fun getReviewDetail(university: Int) {
        viewModelScope.launch {
            kotlin.runCatching { homeRepository.getUnivReview(university) }
                .onSuccess {
                    _reviewDetail.value = it
                    Timber.d("학교별 리뷰 : 서버통신 성공")
                }
                .onFailure {
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
                    Timber.d("선배 랭킹 : 서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    fun getAppBanner(type : String) {
        viewModelScope.launch {
            kotlin.runCatching { appRepository.getAppBanner(type) }
                .onSuccess {
                    _bannerData.value = it
                    Timber.d("앱 배너 리스트 : 서버통신 성공")
                }
                .onFailure {
                    Timber.d("앱 배너 리스트 : 서버통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }
}