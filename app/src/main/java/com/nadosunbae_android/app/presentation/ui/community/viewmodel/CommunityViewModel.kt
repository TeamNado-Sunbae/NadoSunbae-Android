package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.favorites.FavoritesData
import com.nadosunbae_android.domain.model.favorites.FavoritesParam
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.post.PostData
import com.nadosunbae_android.domain.repository.favorites.FavoritesRepository
import com.nadosunbae_android.domain.repository.major.MajorRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val favoritesRepository: FavoritesRepository,
    private val majorRepository: MajorRepository
) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()


    private var _filterMajor = MutableStateFlow(SelectableData.DEFAULT)
    val filterMajor: StateFlow<SelectableData>
        get() = _filterMajor

    //메인 데이터
    private var _communityMainData = MutableStateFlow(listOf(PostData.DEFAULT))
    val communityMainData: StateFlow<List<PostData>>
        get() = _communityMainData

    //커뮤니티 메인 필터 데이터
    private var _communityMainFilterData = MutableStateFlow(listOf(PostData.DEFAULT))
    val communityMainFilterData: StateFlow<List<PostData>>
        get() = _communityMainFilterData

    //커뮤니티 카테고리
    private var _communityMainType = MutableLiveData("")
    val communityMainType: LiveData<String>
        get() = _communityMainType

    //커뮤니티 학과 즐겨찾기
    private var _communityFavorites = MutableStateFlow(FavoritesData.DEFAULT)
    val communityFavorites: StateFlow<FavoritesData>
        get() = _communityFavorites


    //커뮤니티 카테고리 데이터
    fun setCommunityMainType(num: Int) {
        _communityMainType.value = when (num) {
            3 -> "정보"
            2 -> "질문"
            1 -> "자유"
            else -> null
        }
    }

    //커뮤니티 학과 과목
    private var _communityMainMajorName = MutableLiveData("")
    val communityMainMajorName: LiveData<String>
        get() = _communityMainMajorName

    //커뮤니티 학과 과목
    fun setCommunityMainMajorName(majorName: String?) {
        _communityMainMajorName.value = majorName
    }

    //커뮤니티 메인 데이터 호출
    fun getCommunityMainData(
        universityId: String,
        majorId: String?,
        filter: String,
        sort: String,
        search: String? = "",
        type: String? = "",
        majorName: String? = ""
    ) = viewModelScope.launch {
        postRepository.getPost(universityId, majorId, filter, sort, search)
            .onStart {
                onLoadingEnd.value = false
            }.catch {
                Timber.d("커뮤니티 메인 서버통신 오류 발생")
            }.collectLatest {
                Timber.d("커뮤니티 메인 서버통신 $it")
                _communityMainData.value = it
                setCommunityMainFilter(type, majorName)
            }.also {
                onLoadingEnd.value = true
            }
    }

    // 타입 및 과목을 통한 데이터 변경
    fun setCommunityMainFilter(type: String? = "", majorName: String? = "") {
        val typeFlow = flow { emit(type) }
        val filterFlow = flow { emit(majorName) }
        viewModelScope.launch {
            typeFlow.combine(filterFlow) { type, majorName ->
                if (!type.isNullOrEmpty() && !majorName.isNullOrEmpty()) {
                    _communityMainData.value.filter { it.type == type && it.majorName == majorName }
                } else if (!type.isNullOrEmpty() && majorName.isNullOrEmpty()) {
                    _communityMainData.value.filter { it.type == type }
                } else if (type.isNullOrEmpty() && !majorName.isNullOrEmpty()) {
                    _communityMainData.value.filter { it.majorName == majorName }
                } else {
                    _communityMainData.value
                }
            }.collectLatest {
                _communityMainFilterData.value = it
            }
        }
    }

    //커뮤니티 메인 학과 즐겨 찾기
    fun postCommunityFavorite(majorId: Int) {
        viewModelScope.launch {
            majorRepository.deleteMajorList()
            favoritesRepository.postFavorites(
                FavoritesParam(majorId)
            ).catch {
                Timber.d("즐겨찾기 실패")
            }
                .collectLatest {
                    _communityFavorites.value = it
                }
        }
    }

}