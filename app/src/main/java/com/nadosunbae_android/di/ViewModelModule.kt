package com.nadosunbae_android.di

import com.nadosunbae_android.presentation.ui.classroom.viewmodel.*
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.presentation.ui.notification.viewmodel.NotificationViewModel
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewDetailViewModel
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewListViewModel
import com.nadosunbae_android.presentation.ui.review.viewmodel.ReviewWriteViewModel
import com.nadosunbae_android.presentation.ui.sign.SignUpBasicInfoActivity
import com.nadosunbae_android.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.presentation.ui.sign.viewmodel.SignViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    //classRoom
    viewModel { ClassRoomViewModel() }
    viewModel { InfoDetailViewModel(get(),get()) }
    viewModel { QuestionDetailViewModel(get(),get()) }
    viewModel { QuestionWriteViewModel(get()) }
    viewModel { SeniorPersonalViewModel(get(),get()) }


    //main
    viewModel { MainViewModel(get(),get()) }

    //myPage
    viewModel { MyPageViewModel() }

    //notification
    viewModel {NotificationViewModel(get(),get(),get())}

    //review
    viewModel {ReviewDetailViewModel()}
    viewModel { ReviewListViewModel() }
    viewModel { ReviewWriteViewModel() }

    //sign
    viewModel { SignUpBasicInfoViewModel() }
    viewModel { SignViewModel()}
}