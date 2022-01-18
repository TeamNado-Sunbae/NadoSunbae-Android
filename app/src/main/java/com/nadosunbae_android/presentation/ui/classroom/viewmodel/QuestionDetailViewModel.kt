package com.nadosunbae_android.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.data.repository.classroom.ClassRoomRepositoryImpl

class QuestionDetailViewModel : ViewModel() {
    val classRoomRepository: ClassRoomRepository = ClassRoomRepositoryImpl()

    //전체 질문 상세보기 데이터
    private var _questionDetailData = MutableLiveData<ResponseClassRoomQuestionDetail>()
    val questionDetailData : LiveData<ResponseClassRoomQuestionDetail>
        get() = _questionDetailData






    //전체 질문 상세보기 서버 통신
    fun getClassRoomQuestionDetail(postId : Int){
        classRoomRepository.getClassRoomQuestionDetail(postId,
            onResponse = {
                if(it.isSuccessful){
                    _questionDetailData.value = it.body()
                    Log.d("classRoomDetail", "메인 서버 통신 성공")
                }},
            onFailure = {
                it.printStackTrace()
                Log.d("classRoomMain", "메인 서버 통신 실패")
            }
        )

    }
}