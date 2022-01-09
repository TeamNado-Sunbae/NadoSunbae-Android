package com.nadosunbae_andorid.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivityMainBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import com.nadosunbae_andorid.presentation.ui.classroom.ClassRoomFragment
import com.nadosunbae_andorid.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_andorid.presentation.ui.mypage.MyPageFragment
import com.nadosunbae_andorid.presentation.ui.notification.NotificationFragment
import com.nadosunbae_andorid.presentation.ui.review.ReviewFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
               return MainViewModel() as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNav()
    }


    //바텀네비
    private fun initBottomNav(){
        binding.btNvMain.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_review -> {
                    changeFragment(ReviewFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_room -> {
                    mainViewModel.classRoomNum.value = 1
                    changeFragment(ClassRoomFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_notice -> {
                    changeFragment(NotificationFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_mypage -> {
                    changeFragment(MyPageFragment())
                    return@setOnItemSelectedListener true
                }
            }
            true
        }
    }

    private fun changeFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, fragment)
            .addToBackStack(null)
            .commit()
    }



}