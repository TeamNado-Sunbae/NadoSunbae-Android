package com.nadosunbae_android.app.presentation.ui.community.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nadosunbae_android.app.databinding.CustomSwitchTabBinding
import timber.log.Timber

class CustomSwitchTab(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private var binding: CustomSwitchTabBinding
    private var titleOne: TextView
    private var titleTwo: TextView
    private var titleThree: TextView
    private var titleFour: TextView

    //텍스트 클릭
    var itemClickListener: ((Int) -> Unit)? = null

    //텍스트 전달
    private val _switchText = mutableListOf<String?>()
    var switchText: List<String?> = _switchText
        set(value) {
            _switchText.clear()
            _switchText.addAll(value)
            when (_switchText.size) {
                2 -> {
                    titleThree.visibility = View.GONE
                    titleFour.visibility = View.GONE
                }
                3 -> {
                    titleFour.visibility = View.GONE
                }
            }
            setTitle(_switchText)
        }

    //화면 클릭 전달
    private val _switchTab = mutableListOf<Boolean>()
    var switchTab: List<Boolean> = _switchTab
        set(value) {
            _switchTab.clear()
            _switchTab.addAll(value)
            setTab()
            checkSelector()
        }


    init {
        binding = CustomSwitchTabBinding.inflate(LayoutInflater.from(context), this, true)
        titleOne = binding.switchTabOne
        titleTwo = binding.switchTabTwo
        titleThree = binding.switchTabThree
        titleFour = binding.switchTabFour
    }

    //타이틀 선택
    private fun setTitle(list: List<String?>) {
        titleOne.text = list[0]
        titleTwo.text = list[1]
        when (list.size) {
            3 ->
                titleThree.text = list[2]
            4 -> {
                titleThree.text = list[2]
                titleFour.text = list[3]
            }

        }
    }

    //탭 클릭
    private fun setTab() {
        titleOne.setOnClickListener {
            itemClickListener?.invoke(0)
            Timber.d("클릭")
        }
        titleTwo.setOnClickListener {
            itemClickListener?.invoke(1)
        }
        titleThree.setOnClickListener {
            itemClickListener?.invoke(2)
        }
        titleFour.setOnClickListener {
            itemClickListener?.invoke(3)
        }
    }

    private fun checkSelector() {
        titleOne.isSelected = _switchTab[0]
        titleTwo.isSelected = _switchTab[1]
        titleThree.isSelected = _switchTab[2]
        titleFour.isSelected = _switchTab[3]
    }

    companion object {
        fun getSwitchTabValue(fragNum: Int): List<Boolean> {
            return when (fragNum) {
                3 -> listOf(false, false, false, true)
                2 -> listOf(false, false, true, false)
                1-> listOf(false, true, false, false)
                else -> listOf(true, false, false, false)
            }
        }
    }
}