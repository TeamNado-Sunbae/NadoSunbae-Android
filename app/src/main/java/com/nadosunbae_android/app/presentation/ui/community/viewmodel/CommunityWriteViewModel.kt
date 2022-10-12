package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.domain.model.favorites.FavoritesData
import com.nadosunbae_android.domain.model.favorites.FavoritesParam
import com.nadosunbae_android.domain.model.main.MajorSelectData
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.model.post.PostWriteData
import com.nadosunbae_android.domain.model.post.PostWriteParam
import com.nadosunbae_android.domain.repository.favorites.FavoritesRepository
import com.nadosunbae_android.domain.repository.major.MajorRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityWriteViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val favoritesRepository: FavoritesRepository,
    private val majorRepository: MajorRepository
) : ViewModel(), LoadableViewModel {
    override val onLoadingEnd = MutableLiveData<Boolean>()

    //학과 변경 리스트
    private var _majorList = MutableLiveData<List<MajorListData>>()
    val majorList: LiveData<List<MajorListData>>
        get() = _majorList

    fun setMajorList(data: List<MajorListData>) {
        _majorList.value = data
    }

    //학과 선택 내용
    private var _filter = MutableStateFlow(SelectableData.COMMUNITYDEFAULT)
    val filter: StateFlow<SelectableData>
        get() = _filter

    fun setFilter(filter: SelectableData) {
        _filter.value = filter
    }

    //완료 체크
    var writeTitle = MutableStateFlow("")
    var writeContent = MutableStateFlow("")

    private var _completeButton = MutableStateFlow(false)
    val completeButton: StateFlow<Boolean>
        get() = _completeButton

    fun setCompleteButton() {
        viewModelScope.launch {
            combine(writeTitle, writeContent) { title, content ->
                title.isNotEmpty() && content.isNotEmpty()
            }.collectLatest {
                _completeButton.value = it
            }
        }
    }

    // 선택 학과
    private var _selectedMajor = MutableLiveData(MajorSelectData.DEFAULT)
    val selectedMajor: LiveData<MajorSelectData>
        get() = _selectedMajor

    fun setSelectedMajor(data : MajorSelectData){
        _selectedMajor.value = data
    }

    //게시글 작성 서버
    private var _postWrite = MutableStateFlow(PostWriteData.DEFAULT)
    val postWrite: StateFlow<PostWriteData>
        get() = _postWrite

    fun postWrite(type: () -> String, title: String, content: String) {
        viewModelScope.launch {
            postRepository.postWrite(
                PostWriteParam(
                    type = type(),
                    majorId = filter.value.id.toString(),
                    answerId = MainGlobals.signInData?.userId.toString(),
                    title = title,
                    content = content
                )
            ).catch {
                Timber.d("작성 실패")
            }.collectLatest {
                Timber.d("작성 성공")
                _postWrite.value = it
            }.also {
                onLoadingEnd.value = true
            }
        }
    }
    //커뮤니티 학과 즐겨찾기
    private var _communityFavorites = MutableStateFlow(FavoritesData.DEFAULT)
    val communityFavorites: StateFlow<FavoritesData>
        get() = _communityFavorites


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

    //TODO 회원가입시에 가져오기? 근데 회원가입 없는 기존에 사람들은? 이건 고민필요
    //학과 리스트 가져오기
    fun getMajorList(
        universityId: Int, filter: String, exclude: String?,
        userId: Int
    ) {
        viewModelScope.launch {
            majorRepository.getMajorList(universityId, filter, exclude, userId)
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("학과 리스트 가져오기 실패 ${it.printStackTrace()}")

                }
                .collectLatest {
                    _majorList.value = it
                    Timber.d("학과 리스트 $it")
                }
        }
    }
}