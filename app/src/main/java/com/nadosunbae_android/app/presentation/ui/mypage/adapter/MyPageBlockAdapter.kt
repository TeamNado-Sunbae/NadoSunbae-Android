package com.nadosunbae_android.app.presentation.ui.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemMypageBlockBinding
import com.nadosunbae_android.domain.model.mypage.MyPageBlockData

class MyPageBlockAdapter(private val userId: Int) :
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
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
            Log.d("user", ":" + myPageBlockData[position].nickname)
            Log.d("userId", ":" + myPageBlockData[position].userId)
            Log.d("userImg", ":" + myPageBlockData[position].profileImageId)
        }
    }

    override fun getItemCount(): Int = myPageBlockData.size

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

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener

    }
    fun removeItem(itemPosition : Int){
        myPageBlockData.removeAt(itemPosition)
        notifyItemRemoved(itemPosition)
    }

    fun setBlockMain(myPageBlockData: MutableList<MyPageBlockData.Data>) {
        this.myPageBlockData = myPageBlockData
        notifyDataSetChanged()
    }



}