package com.nadosunbae_android.app.presentation.ui.sign.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemBottomsheetListBinding
import com.nadosunbae_android.app.databinding.ItemBottomshhetCommunityListBinding
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.app.util.setTextSemiBold
import com.nadosunbae_android.domain.model.main.SelectableData
import timber.log.Timber

class MajorSelectAdapter(private val noMajor: Int? = -2, private val communityWrite: Boolean? = false, private val isSignUp : Boolean?= false) :
    ListAdapter<SelectableData, RecyclerView.ViewHolder>(
        DiffUtilCallback<SelectableData>()
    ) {

    private var mSelectedPos: Int = NOT_SELECTED

    private val _selectedData = MutableLiveData<SelectableData>()
    val selectedData: LiveData<SelectableData>
        get() = _selectedData

    private var favoriteCompleteListener: (Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == COMMUNITY) {
            val binding = ItemBottomshhetCommunityListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            BottomSheetSelectionViewHolder(binding)
        } else {
            val binding = ItemBottomsheetListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SignSelectionViewHolder(binding)
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        if (holder is SignSelectionViewHolder) {
            holder.onBind(getItem(position))

            if(isSignUp == true) {
                holder.binding.btnMajorStar.visibility = View.GONE
            }
            holder.binding.btnMajorStar.setOnClickListener {
                favoriteCompleteListener.let {
                    it(getItem(position).id)
                }
            }
        } else if (holder is BottomSheetSelectionViewHolder) {
            holder.onBind(getItem(position))
        }

        holder.itemView.setOnClickListener {
            when (mSelectedPos) {
                // 새로 선택
                NOT_SELECTED -> {
                    mSelectedPos = position
                    getItem(position).isSelected = true
                }
                // 선택 해제
                position -> {
                    if (communityWrite == false) {
                        mSelectedPos = NOT_SELECTED
                        getItem(position).isSelected = false
                    }
                }
                // 선택 변경
                else -> {
                    Timber.d("변경")
                    getItem(mSelectedPos).isSelected = false
                    mSelectedPos = position
                    getItem(position).isSelected = true
                }
            }
            _selectedData.value = getSelectedData()
            notifyDataSetChanged()
        }
    }


    class SignSelectionViewHolder(val binding: ItemBottomsheetListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(bottomSheetData: SelectableData) {
            binding.data = bottomSheetData
            binding.btnMajorStar.isSelected = bottomSheetData.isFavorites ?: false
            binding.tvBottomsheeetContent.isSelected = bottomSheetData.isSelected
            if (bottomSheetData.isSelected) {
                binding.ivBottomsheetCheck.visibility = View.VISIBLE
                binding.tvBottomsheeetContent.setTextSemiBold(true)
            } else {
                binding.ivBottomsheetCheck.visibility = View.INVISIBLE
                binding.tvBottomsheeetContent.setTextSemiBold(false)
            }

            binding.executePendingBindings()
        }
    }

    //커뮤니티 버전
    class BottomSheetSelectionViewHolder(val binding: ItemBottomshhetCommunityListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(bottomSheetData: SelectableData) {
            with(binding) {
                data = bottomSheetData
                bottomSheetData.isSelected.apply {
                    tvBottomsheeetContent.isSelected = this
                    check = this
                    tvBottomsheeetContent.setTextSemiBold(this)
                }
                executePendingBindings()
            }
        }
    }


    private fun getSelectedData(): SelectableData {
        if (mSelectedPos != NOT_SELECTED)
            return currentList[mSelectedPos]
        return SelectableData(-1, "", false)
    }

    fun setSelectedData(dataId: Int) {
        for (d in currentList) {
            if (d.id == dataId) {
                mSelectedPos = currentList.indexOf(d)
                d.isSelected = true
                break
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).id == noMajor) {
            COMMUNITY
        } else {
            ANOTHER
        }

    }

    fun clearSelect() {
        for (d in currentList)
            d.isSelected = false
        mSelectedPos = NOT_SELECTED
    }

    //즐겨찾기 클릭
    fun setFavoritesClickListener(listener: (Int) -> Unit) {
        this.favoriteCompleteListener = listener
    }

    companion object {
        const val NOT_SELECTED = -1
        const val COMMUNITY = 0
        const val ANOTHER = 1
    }


}