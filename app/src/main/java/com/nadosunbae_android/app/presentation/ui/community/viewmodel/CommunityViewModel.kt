package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.post.PostData
import com.nadosunbae_android.domain.repository.post.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()



    var filterMajor = MutableLiveData("학과")

    private var _communityMainData = MutableStateFlow(listOf(PostData.DEFAULT))
    val communityMainData: StateFlow<List<PostData>>
        get() = _communityMainData


    //커뮤니티 메인 데이터 호출
    fun getCommunityMainData(
        universityId : String,
        majorId: String?,
        filter: String,
        sort: String,
        search : String ?= ""
    ) = viewModelScope.launch {
        postRepository.getPost(universityId,majorId,filter,sort,search)
            .onStart {
                onLoadingEnd.value = false
            }.catch {
                Timber.d("커뮤니티 메인 서버통신 오류 발생")
            }.collectLatest {
                _communityMainData.value = it
            }
    }
}