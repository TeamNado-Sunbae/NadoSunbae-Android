package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.classroom.ClassRoomPostWriteData
import com.nadosunbae_android.domain.model.classroom.ClassRoomPostWriteItem
import com.nadosunbae_android.domain.model.classroom.WriteUpdateData
import com.nadosunbae_android.domain.model.classroom.WriteUpdateItem
import com.nadosunbae_android.domain.usecase.classroom.PostClassRoomWriteUseCase
import com.nadosunbae_android.domain.usecase.classroom.PutWriteUpdateUseCase
import kotlinx.coroutines.launch

class QuestionWriteViewModel(
    val postClassRoomWriteUseCase: PostClassRoomWriteUseCase,
    val putWriteUpdateUseCase: PutWriteUpdateUseCase
) : ViewModel() {

    //전체 질문글 작성 제목 및 내용 있는지 체크
    var title = MutableLiveData<Boolean>()
    var content = MutableLiveData<Boolean>()

    //전체 질문글 작성 제목 및 내용의 내용
    var titleData = MutableLiveData<String>()
    var contentData = MutableLiveData<String>()

    //작성시 필요한 데이터
    var majorId = MutableLiveData<Int>()
    var answerId = MutableLiveData<Int>()
    var postTypeId = MutableLiveData<Int>()


    //1:1, 전체 질문글, 정보글 작성
    var postDataWrite : MutableLiveData<ClassRoomPostWriteData> = MutableLiveData()

    //1:1, 전체 질문글, 정보글 수정
    private var _writeUpdate = MutableLiveData<WriteUpdateData>()
    val writeUpdateData : LiveData<WriteUpdateData>
        get() = _writeUpdate

    var completeBtn = MediatorLiveData<Boolean>().apply {
        this.addSource(title){
            this.value = isCompleteBtn()
        }
        this.addSource(content){
            this.value = isCompleteBtn()
        }
    }

    //완료 버튼 활성화
    private fun isCompleteBtn() : Boolean{
        return (title.value == true) && (content.value == true)
    }

    //1:1, 질문, 정보글 등록
    fun postClassRoomWrite(classRoomPostWriteItem: ClassRoomPostWriteItem){
        viewModelScope.launch {
            runCatching { postClassRoomWriteUseCase(classRoomPostWriteItem) }
                .onSuccess {
                    postDataWrite.value = it

                    when (classRoomPostWriteItem.postTypeId) {
                        2 -> FirebaseAnalyticsUtil.userPost(FirebaseAnalyticsUtil.Post.INFORMATION)
                        3 -> FirebaseAnalyticsUtil.userPost(FirebaseAnalyticsUtil.Post.QUESTION_ALL)
                        4 -> {
                            FirebaseAnalyticsUtil.userPost(FirebaseAnalyticsUtil.Post.QUESTION_PERSONAL)
                            FirebaseAnalyticsUtil.question(FirebaseAnalyticsUtil.Question.QUESTION_START)
                        }
                    }

                    Log.d("classRoomWrite", "글 작성 등록 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("classRoomWrite", "글 작성 등록 실패")
                }
        }
    }

    //1:1, 질문, 정보글 수정
    fun putWriteUpdate(postId : Int, writeUpdateItem: WriteUpdateItem){
        viewModelScope.launch {
            runCatching { putWriteUpdateUseCase(postId, writeUpdateItem) }
                .onSuccess {
                    _writeUpdate.value = it
                    Log.d("writeUpdate", "글 수정 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("writeUpdate", "글 수정 실패")
                }
        }

    }


}