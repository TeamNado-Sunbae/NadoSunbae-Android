package com.nadosunbae_android.app.di

import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.*
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.notification.viewmodel.NotificationViewModel
import com.nadosunbae_android.app.presentation.ui.review.viewmodel.ReviewDetailViewModel
import com.nadosunbae_android.app.presentation.ui.review.viewmodel.ReviewListViewModel
import com.nadosunbae_android.app.presentation.ui.review.viewmodel.ReviewWriteViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel
import com.nadosunbae_android.app.presentation.ui.sign.viewmodel.SignViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val viewModelModule = module {

    //classRoom
    viewModel { ClassRoomViewModel() }
    viewModel { InfoDetailViewModel(get(),get()) }
    viewModel { QuestionDetailViewModel(get(),get()) }
    viewModel { QuestionWriteViewModel(get()) }
    viewModel { SeniorPersonalViewModel(get(),get()) }
    viewModel { AskEveryOneViewModel()}

    //main
    viewModel { MainViewModel(get(),get(),get()) }

    //myPage
    viewModel { MyPageViewModel(get(),get(), get()) }

    //notification
    viewModel {NotificationViewModel(get(),get(),get())}

    //review
    viewModel {ReviewDetailViewModel(get(),get(),get())}
    viewModel { ReviewListViewModel(get(),get()) }
    viewModel { ReviewWriteViewModel(get(),get(),get()) }

    //sign
    viewModel { SignUpBasicInfoViewModel(get(),get(),get(),get(),get(),get())}
    viewModel { SignViewModel()}
}