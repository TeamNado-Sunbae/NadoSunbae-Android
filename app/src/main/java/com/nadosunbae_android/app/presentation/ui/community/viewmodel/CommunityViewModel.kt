package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.main.SelectableData
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

    var communityMainType = MutableLiveData("")
    var communityMainMajorName = MutableLiveData("")

    //커뮤니티 메인 데이터 호출
    fun getCommunityMainData(
        universityId: String,
        majorId: String?,
        filter: String,
        sort: String,
        search: String? = ""
    ) = viewModelScope.launch {
        postRepository.getPost(universityId, majorId, filter, sort, search)
            .onStart {
                onLoadingEnd.value = false
            }.catch {
                Timber.d("커뮤니티 메인 서버통신 오류 발생")
            }.collectLatest {
                Timber.d("커뮤니티 메인 서버통신 ")
                _communityMainData.value = it
                setCommunityMainFilter()
            }
    }

    fun setCommunityMainFilter(type: String?="", majorName: String?="") {
        val typeFlow = flow { emit(type) }
        val filterFlow = flow { emit(majorName) }
        viewModelScope.launch {
            typeFlow.combine(filterFlow) { type, majorName ->
                if (!type.isNullOrEmpty() && !majorName.isNullOrEmpty()) {
                    _communityMainData.value.filter { it.type == type && it.majorName == majorName }
                }else if(!type.isNullOrEmpty() && majorName.isNullOrEmpty()){
                    _communityMainData.value.filter {it.type == type}
                }else if(type.isNullOrEmpty() && !majorName.isNullOrEmpty()){
                    _communityMainData.value.filter{it.majorName == majorName}
                }else{
                    _communityMainData.value
                }
            }.collectLatest {
                _communityMainFilterData.value = it
            }
        }
    }
}