package com.nadosunbae_android.presentation.ui.sign.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.nadosunbae_android.R
import com.nadosunbae_android.databinding.ItemSignupSpinnerBinding
import com.nadosunbae_android.databinding.SpinnerItemBinding

class SpinnerAdapter(
    context: Context,
    @LayoutRes private var resId: Int,
    private var values: List<String>
) : ArrayAdapter<String>(context, resId, values) {

    override fun getCount() = values.size

    override fun getItem(position: Int) = values[position]

    //눈에 보여지는 Spinner 모습
    //클릭안했을때
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSignupSpinnerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_signup_spinner, parent, false
        )
        // 생명주기를 갖고있지 않음
        // 리턴을 한 다음에 생명주기가 정의돔
        // 그래서 뷰가 그려지기 전에 설정들을 다 할 것!!

        binding.textSignupMajorinfoMajor.text = "고려대학교"
        binding.textSignupMajorinfoMajor.setTextColor(Color.parseColor("#001c18"))


        return binding.root
    }

    //메뉴 나타났을떄
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: SpinnerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.spinner_item, parent, false
        )

        binding.textSpinner.text = values[position]

        if (binding.textSpinner.text == "타 대학은 현재 준비중입니다") {
            binding.textSpinner.isSelected = true
            binding.imgSpinnerCheck.isSelected = false
        } else {
            binding.textSpinner.isSelected = false
            binding.imgSpinnerCheck.isSelected = true
        }


        return binding.root
    }
}