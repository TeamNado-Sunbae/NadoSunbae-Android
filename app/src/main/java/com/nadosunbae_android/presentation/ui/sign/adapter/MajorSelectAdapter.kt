package com.nadosunbae_android.presentation.ui.sign.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.sign.BottomSheetData
import com.nadosunbae_android.databinding.ItemBottomsheetListBinding
import com.nadosunbae_android.presentation.ui.sign.CustomBottomSheetDialog
import com.nadosunbae_android.util.setTextSemiBold

class MajorSelectAdapter(
    var link : CustomBottomSheetDialog.DataToFragment
) : RecyclerView.Adapter<MajorSelectAdapter.SignSelectionViewHolder>() {
    var dataList = mutableListOf<BottomSheetData>()
    private var mSelectedPos: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MajorSelectAdapter.SignSelectionViewHolder {
        val binding = ItemBottomsheetListBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return SignSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MajorSelectAdapter.SignSelectionViewHolder,
                                  @SuppressLint("RecyclerView") position: Int) {
        holder.onBind(dataList[position])

        holder.itemView.setOnClickListener {


            when (mSelectedPos) {
                // 새로 선택
                NOT_SELECTED -> {
                    mSelectedPos = position
                    dataList[position].isSelected = true
                    link.getBtnSelector(true)
                    link.getEditTextSelector(dataList[position].name)
                }

                // 선택 해제
                position -> {
                    mSelectedPos = NOT_SELECTED
                    dataList[position].isSelected = false
                    link.getBtnSelector(false)
                }

                // 선택 변경
                else -> {
                    dataList[mSelectedPos].isSelected = false
                    mSelectedPos = position
                    dataList[position].isSelected = true
                    link.getBtnSelector(true)
                    link.getEditTextSelector(dataList[position].name)
                }

            }

            notifyDataSetChanged()
        }



    }





    override fun getItemCount(): Int = dataList.size

    inner class SignSelectionViewHolder(val binding : ItemBottomsheetListBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(bottomSheetData : BottomSheetData){
            binding.data = bottomSheetData
            binding.tvBottomsheeetContent.isSelected = bottomSheetData.isSelected

            if (bottomSheetData.isSelected) {
                binding.ivBottomsheetCheck.visibility = View.VISIBLE
                binding.tvBottomsheeetContent.setTextSemiBold(true)
            }
            else {
                binding.ivBottomsheetCheck.visibility = View.INVISIBLE
                binding.tvBottomsheeetContent.setTextSemiBold(false)
            }


            binding.executePendingBindings()
        }


    }


    companion object {
        const val NOT_SELECTED = -1
    }
}