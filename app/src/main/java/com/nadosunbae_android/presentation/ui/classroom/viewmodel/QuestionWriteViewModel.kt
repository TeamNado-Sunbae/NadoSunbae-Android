package com.nadosunbae_android.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.model.response.classroom.ResponseClassRoomWriteData
import com.nadosunbae_android.repositoryimpl.classroom.ClassRoomRepository
import com.nadosunbae_android.repositoryimpl.classroom.ClassRoomRepositoryImpl

class QuestionWriteViewModel : ViewModel() {
    private val classRoomRepository : ClassRoomRepository = ClassRoomRepositoryImpl()
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
    var postDataWrite : MutableLiveData<ResponseClassRoomWriteData> = MutableLiveData()

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
    fun postClassRoomWrite(requestClassRoomPostData: RequestClassRoomPostData){
        classRoomRepository.postClassRoomWrite(requestClassRoomPostData,
            onResponse = {
                if(it.isSuccessful){
                    postDataWrite.value = it.body()
                    Log.d("classRoomWrite", "글 작성 등록 완료")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d("classRoomWrite", "글 작성 등록 실패")
            }
        )

    }


}