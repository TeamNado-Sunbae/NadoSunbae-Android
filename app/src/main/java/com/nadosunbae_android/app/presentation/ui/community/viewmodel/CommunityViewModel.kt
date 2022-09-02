package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.community.CommunityMainData
import com.nadosunbae_android.domain.repository.community.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()



    var filterMajor = MutableLiveData("학과")

    private var _communityMainData = MutableStateFlow(listOf(CommunityMainData.DEFAULT))
    val communityMainData: StateFlow<List<CommunityMainData>>
        get() = _communityMainData


    //커뮤니티 메인 데이터 호출
    fun getCommunityMainData(
        majorId: String,
        filter: String,
        sort: String
    ) = viewModelScope.launch {
        communityRepository.getCommunityMain(majorId, filter, sort)
            .onStart {
                onLoadingEnd.value = false
            }.catch {
                Timber.d("커뮤니티 메인 서버통신 오류 발생")
            }.collectLatest {
                _communityMainData.value = it
            }
    }
}