package com.nadosunbae_android.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.response.sign.SelectableData
import com.nadosunbae_android.databinding.ItemDropDownBinding

class CustomDropDownAdapter : RecyclerView.Adapter<CustomDropDownAdapter.DropDownViewHolder>() {

    var dataList = mutableListOf<SelectableData>()

    class DropDownViewHolder(private val binding: ItemDropDownBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SelectableData) {
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

    fun setMenuData(dataList: MutableList<SelectableData>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size
}