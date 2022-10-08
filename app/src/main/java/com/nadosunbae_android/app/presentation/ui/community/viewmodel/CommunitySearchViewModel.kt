package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.LiveData
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
class CommunitySearchViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()

    //커뮤니티 검색 데이터
    private var _communitySearchData = MutableStateFlow(listOf(PostData.DEFAULT))
    val communitySearchData: StateFlow<List<PostData>>
        get() = _communitySearchData

    //커뮤니티 뷰 데이터(검색 결과 보여지기 / 아니기)
    private var _communitySearchView = MutableLiveData(
        SearchView(
            firstView = true,
            contentView = false,
            emptyView = false
        )
    )
    val communitySearchView: LiveData<SearchView>
        get() = _communitySearchView

    fun getCommunitySearchData(searchKeyword: Pair<Int, String>) {
        viewModelScope.launch {
            postRepository.getPost(
                searchKeyword.first,
                null, "community", "like", searchKeyword.second
            )
                .onStart {
                    onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("커뮤니티 검색 서버 통신 실패")
                }
                .collectLatest {
                    _communitySearchView.value = SearchView(
                        firstView = false,
                        contentView = it.isNotEmpty(),
                        emptyView = it.isEmpty()
                    )
                    _communitySearchData.value = it
                }
        }
    }

}

data class SearchView(var firstView: Boolean, val contentView: Boolean, val emptyView: Boolean)