package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.classroom.SeniorPersonalData
import com.nadosunbae_android.domain.model.favorites.FavoritesData
import com.nadosunbae_android.domain.model.favorites.FavoritesParam
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.domain.repository.favorites.FavoritesRepository
import com.nadosunbae_android.domain.repository.major.MajorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClassRoomMainContentViewModel @Inject constructor(
    private val majorRepository: MajorRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    val curFragment: MutableLiveData<Int>
        get() = ClassRoomMainContentViewModel.curFragment

    private var _classRoomFavorites = MutableStateFlow(FavoritesData.DEFAULT)
    val classRoomFavorites
        get() = _classRoomFavorites

    companion object {
        val curFragment = MutableLiveData(-1)
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
                    _classRoomFavorites.value = it
                }
        }
    }

}