package com.nadosunbae_android.app.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.app.AppBannerData
import com.nadosunbae_android.domain.model.home.HomeRankingData
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData
import com.nadosunbae_android.domain.model.post.PostData
import com.nadosunbae_android.domain.repository.app.AppRepository
import com.nadosunbae_android.domain.repository.home.HomeRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val homeRepository: HomeRepository,
    private val appRepository: AppRepository
) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>(false)

    var userId = MutableLiveData<Int>()

    //1:1질문
    private var _personToQuestionData = MutableStateFlow(listOf(PostData.DEFAULT))
    val personToQuestionData: StateFlow<List<PostData>>
        get() = _personToQuestionData

    //커뮤니티
    private var _communityData = MutableStateFlow(listOf(PostData.DEFAULT))
    val communityData: StateFlow<List<PostData>>
        get() = _communityData

    private val _reviewDetail = MutableLiveData<List<HomeUnivReviewData>>()
    val reviewDetail: LiveData<List<HomeUnivReviewData>>
        get() = _reviewDetail

    private val _rankingData = MutableLiveData<List<HomeRankingData>>()
    val rankingData: LiveData<List<HomeRankingData>>
        get() = _rankingData

    private val _bannerData = MutableLiveData<List<AppBannerData>>()
    val bannerData: LiveData<List<AppBannerData>>
        get() = _bannerData

    //홈뷰 더보기 GA
    private var _homeViewGA = MutableLiveData<Int>()
    val homeViewGA : LiveData<Int>
        get() = _homeViewGA

    fun setHomeViewGA(num : Int){
        _homeViewGA.value = num
    }


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

    //커뮤니티 메인 데이터 호출
    fun getQuestionMainData(
        universityId: Int,
        majorId: String?,
        filter: String,
        sort: String,
        search: String? = ""
    ) = viewModelScope.launch {
        postRepository.getPost(universityId, majorId, filter, sort, search)
            .catch {
                Timber.d("커뮤니티 메인 서버통신 오류 발생")
            }.collectLatest {
                Timber.d("커뮤니티 메인 서버통신 $it")
                _personToQuestionData.value = it
            }.also {
                onLoadingEnd.value = true
            }
    }
}