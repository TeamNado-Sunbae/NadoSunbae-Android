package com.nadosunbae_android.presentation.ui.classroom.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionWriteViewModel : ViewModel() {

    //전체 질문글 작성 제목 및 내용
    var title = MutableLiveData<Boolean>()
    var content = MutableLiveData<Boolean>()

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


}