package com.nadosunbae_android.app.presentation.ui.review.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.ui.SelectBackgroundBoxData
import com.nadosunbae_android.databinding.ItemListBackgroundBinding
import com.nadosunbae_android.app.util.getBackgroundImage

class ReviewSelectBackgroundAdapter : RecyclerView.Adapter<ReviewSelectBackgroundAdapter.ReviewSelectBackgroundHolder>() {
    var dataList = mutableListOf<SelectBackgroundBoxData>()

    private val _mSelectedPos = MutableLiveData(NOT_SELECTED)
    val mSelectedPos: LiveData<Int>
        get() = _mSelectedPos

    class ReviewSelectBackgroundHolder(val binding: ItemListBackgroundBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SelectBackgroundBoxData) {

            // 배경 id 따라서 클라에서 처리하도록 분기처리
            binding.imageResId = getBackgroundImage(data.imageId)

            /*
            url로 load할 때 사용 (현재는 클라에서 id로 분기처리)
            binding.imageUrl = data.imageUrl
             */
            if (data.isSelected) {
                binding.clSelectedBackground.visibility = View.VISIBLE
            }
            else {
                binding.clSelectedBackground.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewSelectBackgroundHolder {
        var binding = ItemListBackgroundBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return ReviewSelectBackgroundHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewSelectBackgroundHolder,
                                  @SuppressLint("RecyclerView") position: Int) {
        holder.onBind(dataList[position])

        holder.itemView.setOnClickListener {


            when (_mSelectedPos.value) {
                // 새로 선택할 때
                NOT_SELECTED -> {
                    _mSelectedPos.value = position
                    dataList[position].isSelected = true
                }

                // 선택된 배경을 다시 선택했을 경우 -> 선택 해제
                position -> {
                    _mSelectedPos.value = NOT_SELECTED
                    dataList[position].isSelected = false
                }

                // 다른 배경을 선택했을 경우 -> 선택 변경
                else -> {
                    dataList[mSelectedPos.value!!].isSelected = false
                    _mSelectedPos.value = position
                    dataList[position].isSelected = true
                }
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun getSelectedBackgroundId(): Int? {
        if (mSelectedPos.value == NOT_SELECTED)
            return null
        return dataList[mSelectedPos.value!!].imageId
    }

    // 선택된 배경 설정 (default 지정시에만 사용할 것)
    fun setSelectedBackground(backgroundId: Int) {
        for (d in dataList) {
            if (d.imageId == backgroundId) {
                _mSelectedPos.value = dataList.indexOf(d)
                d.isSelected = true
            }
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    companion object {
        const val NOT_SELECTED = -1
    }

}