package com.nadosunbae_android.app.presentation.ui.sign.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.app.databinding.ItemBottomsheetListBinding
import com.nadosunbae_android.app.util.CustomBottomSheetDialog
import com.nadosunbae_android.app.util.setTextSemiBold

class MajorSelectAdapter(
    var link : CustomBottomSheetDialog.DataToFragment
) : RecyclerView.Adapter<MajorSelectAdapter.SignSelectionViewHolder>() {
    var dataList = mutableListOf<SelectableData>()
    private var mSelectedPos: Int = -1

    private val _selectedData = MutableLiveData<SelectableData>()
    val selectedData: LiveData<SelectableData>
        get() = _selectedData


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
                }

                // 선택 해제
                position -> {
                    mSelectedPos = NOT_SELECTED
                    dataList[position].isSelected = false
                }

                // 선택 변경
                else -> {
                    dataList[mSelectedPos].isSelected = false
                    mSelectedPos = position
                    dataList[position].isSelected = true
                }

            }

            _selectedData.value = getSelectedData()
            notifyDataSetChanged()
        }



    }





    override fun getItemCount(): Int = dataList.size

    inner class SignSelectionViewHolder(val binding : ItemBottomsheetListBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(bottomSheetData : SelectableData){
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



    fun getSelectedData(): SelectableData {
        if (mSelectedPos != NOT_SELECTED)
            return dataList[mSelectedPos]
        return SelectableData(-1, "", false)
    }

    fun setSelectedData(dataId: Int) {
        for (d in dataList) {
            if (d.id == dataId) {
                mSelectedPos = dataList.indexOf(d)
                d.isSelected = true
                break
            }
        }
    }

    fun clearSelect() {
        for (d in dataList)
            d.isSelected = false
        mSelectedPos = NOT_SELECTED
    }

    companion object {
        const val NOT_SELECTED = -1
    }
}