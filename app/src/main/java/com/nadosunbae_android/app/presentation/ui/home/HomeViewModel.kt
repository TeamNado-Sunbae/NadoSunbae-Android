package com.nadosunbae_android.app.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.nadosunbae_android.domain.model.home.HomeCommunityData
import com.nadosunbae_android.domain.model.home.HomeQuestionData
import com.nadosunbae_android.domain.model.home.HomeReviewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    //더미데이터 테스트 -> 뷰 깨지는지 확인
    val reviewData = listOf<HomeReviewData>(
        HomeReviewData("경영학과","난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어최대 40자난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어최대 40자","21/12/23"),
        HomeReviewData("경영학과","난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어최대 40자","21/12/23"),
        HomeReviewData("경영학과","난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어최대 40자","21/12/23"),
        HomeReviewData("경영학과","난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어최대 40자","21/12/23"),
        HomeReviewData("경영학과","난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어최대 40자","21/12/23"),
        HomeReviewData("경영학과","난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어최대 40자","21/12/23"),
        HomeReviewData("경영학과","난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어최대 40자","21/12/23")
    )

    val questionData = listOf<HomeQuestionData>(
        HomeQuestionData("질문자닉네임", "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...", "21/12/23"),
        HomeQuestionData("질문자닉네임", "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...", "21/12/23"),
        HomeQuestionData("질문자닉네임", "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...", "21/12/23"),
        HomeQuestionData("질문자닉네임", "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...", "21/12/23"),
        HomeQuestionData("질문자닉네임", "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...", "21/12/23"),
        HomeQuestionData("질문자닉네임", "209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...209*66 넘으면 ...난 자유롭고 싶어 지금 전투력 수치 111퍼입고싶은 옷 입고싶어 싶은 옷입고싶...", "21/12/23")
    )

    val communityData = listOf<HomeCommunityData>(
        HomeCommunityData("질문", "커뮤니티 제목", "커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용", "학과전체", "오후 5:42", 1,1),
        HomeCommunityData("질문", "커뮤니티 제목", "커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용", "학과전체", "오후 5:42", 1,1),
        HomeCommunityData("질문", "커뮤니티 제목", "커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용", "학과전체", "오후 5:42", 1,1),
        HomeCommunityData("질문", "커뮤니티 제목", "커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용", "학과전체", "오후 5:42", 1,1),
        HomeCommunityData("질문", "커뮤니티 제목", "커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용", "학과전체", "오후 5:42", 1,1),
        HomeCommunityData("질문", "커뮤니티 제목", "커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용 커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용커뮤니티내용 커뮤니티내용", "학과전체", "오후 5:42", 1,1)
    )
}