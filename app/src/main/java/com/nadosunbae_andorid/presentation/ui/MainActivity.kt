package com.nadosunbae_andorid.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivityMainBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import com.nadosunbae_andorid.presentation.ui.classroom.ClassRoomFragment
import com.nadosunbae_andorid.presentation.ui.mypage.MyPageFragment
import com.nadosunbae_andorid.presentation.ui.notification.NotificationFragment
import com.nadosunbae_andorid.presentation.ui.review.ReviewFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNav()
    }


    //바텀네비
    private fun initBottomNav(){
        binding.btNvMain.itemIconTintList = null
        binding.btNvMain.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_review -> {
                    changeFragment(ReviewFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_room -> {
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
            false
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