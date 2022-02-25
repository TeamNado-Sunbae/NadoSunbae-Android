package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypageBlockBinding
import com.nadosunbae_android.domain.model.mypage.MyPageBlockData

class MyPageBlockAdapter() :
    RecyclerView.Adapter<MyPageBlockAdapter.MyPageBlockViewHodler>() {
    var myPageBlockData = mutableListOf<MyPageBlockData.Data>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageBlockViewHodler {
        val binding = ItemMypageBlockBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyPageBlockViewHodler(binding)
    }


    override fun onBindViewHolder(
        holder: MyPageBlockViewHodler,
        position: Int
    ) {
        holder.onBind(myPageBlockData[position])
    }

    override fun getItemCount(): Int {
        return if (myPageBlockData.size < 6) myPageBlockData.size else 5

    }

    inner class MyPageBlockViewHodler(
        val binding: ItemMypageBlockBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myPageBlockData: MyPageBlockData.Data) {
            binding.apply {
                myPageBlock = myPageBlockData
                executePendingBindings()
            }
        }
    }

    fun setBlockMain(myPageBlockData: MutableList<MyPageBlockData.Data>) {
        this.myPageBlockData = myPageBlockData
        notifyDataSetChanged()
    }
}