package com.nadosunbae_android.app.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.sign.SelectableData
import com.nadosunbae_android.databinding.ItemDropDownBinding

class CustomDropDownAdapter(val viewModel: DropDownSelectableViewModel, val selectedItemId: Int) : RecyclerView.Adapter<CustomDropDownAdapter.DropDownViewHolder>() {

    private var dataList = mutableListOf<SelectableData>()

    private var _selectedId = selectedItemId
    val selectedId: Int
        get() = _selectedId

    class DropDownViewHolder(private val binding: ItemDropDownBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SelectableData) {
            if (data.isSelected)
                binding.ivChecked.visibility = View.VISIBLE
            else
                binding.ivChecked.visibility = View.INVISIBLE

            binding.title = data.name
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropDownViewHolder {
        var binding = ItemDropDownBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return DropDownViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: DropDownViewHolder, position: Int) {
        holder.onBind(dataList[position])

        holder.itemView.setOnClickListener {
            viewModel.dropDownSelected.value = dataList[position]
            itemClickListener.onClick(it, position)
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    fun setMenuList(dataList: MutableList<SelectableData>) {
        this.dataList = dataList

        // 초기 선택된 데이터 처리
        for (d in dataList) {
            d.isSelected = d.id == selectedItemId
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size
}