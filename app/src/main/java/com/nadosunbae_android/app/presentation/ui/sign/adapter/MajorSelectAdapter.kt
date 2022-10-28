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

class MajorSelectAdapter(
    private val communityWrite: Boolean? = false,
    private val isSignUp: Boolean? = false
) :
    ListAdapter<SelectableData, RecyclerView.ViewHolder>(
        DiffUtilCallback<SelectableData>()
    ) {

    private var mSelectedPos: Int = NOT_SELECTED

    private val _selectedData = MutableLiveData<SelectableData>()
    val selectedData: LiveData<SelectableData>
        get() = _selectedData

    private var favoriteCompleteListener: (Int) -> Unit = {}

    //검색시 종료되는 부분 해결
    private var name = ""
    private var changeErrorComplete: (String) -> Unit = {}
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

            if (isSignUp == true) {
                holder.binding.btnMajorStar.visibility = View.GONE
            } else if (holder.binding.tvBottomsheeetContent.text.toString() == "미진입") {
                holder.binding.btnMajorStar.visibility = View.INVISIBLE
            }
            holder.binding.btnMajorStar.setOnClickListener {
                favoriteCompleteListener.let {
                    it(getItem(holder.absoluteAdapterPosition).id)
                }
            }
        } else if (holder is BottomSheetSelectionViewHolder) {
            holder.onBind(getItem(position))
        }

        holder.itemView.setOnClickListener {
            mSelectedPos = if (name == getItem(holder.absoluteAdapterPosition).name) {
                EQUAL
            } else if(name == ""){
                NOT_SELECTED
            }else{
                SELECTED
            }

            when (mSelectedPos) {
                // 새로 선택
                NOT_SELECTED -> {
                    name = getItem(holder.absoluteAdapterPosition).name
                    mSelectedPos = SELECTED
                    getItem(holder.absoluteAdapterPosition).isSelected = true
                }
                // 선택 해제
                EQUAL -> {
                    if (communityWrite == false) {
                        mSelectedPos = NOT_SELECTED
                        getItem(holder.absoluteAdapterPosition).isSelected = false
                    }
                }
                // 선택 변경
                else -> {
                    changeErrorComplete(name)
                    if (name != getItem(holder.absoluteAdapterPosition).name) {
                        name = getItem(holder.absoluteAdapterPosition).name
                        getItem(holder.absoluteAdapterPosition).isSelected = true
                    }
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
        if (mSelectedPos != NOT_SELECTED){
            val index = currentList.indexOf(currentList.find { it.name == name })
            return currentList[index]
        }
        return SelectableData(-1, "", false)
    }

    fun setSelectedData(dataId: Int) {
        for (d in currentList) {
            if (d.id == dataId) {
                name = d.name
                d.isSelected = true
                break
            }
        }
    }

    fun setSelectedNameData(dataName : String){
        for (d in currentList) {
            if (d.name == dataName) {
                name = d.name
                d.isSelected = true
                break
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).name == "학과 무관") {
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

    //검색시 오류나는 부분 해결
    fun setChangeErrorComplete(listener: (String) -> Unit) {
        this.changeErrorComplete = listener
    }

    companion object {
        const val NOT_SELECTED = -1
        const val EQUAL = 0
        const val SELECTED = 1
        const val COMMUNITY = 0
        const val ANOTHER = 1
    }


}