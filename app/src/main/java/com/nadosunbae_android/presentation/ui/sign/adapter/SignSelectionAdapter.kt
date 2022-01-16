package com.nadosunbae_android.presentation.ui.sign.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.data.model.sign.ResponseMajorData
import com.nadosunbae_android.databinding.ItemBottomsheetListBinding

class SignSelectionAdapter : RecyclerView.Adapter<SignSelectionAdapter.SignSelectionViewHolder>() {
    var signSelectionData = mutableListOf<ResponseMajorData.Data.Major>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignSelectionAdapter.SignSelectionViewHolder {
        val binding = ItemBottomsheetListBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return SignSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SignSelectionAdapter.SignSelectionViewHolder, position: Int) {
        holder.onBind(signSelectionData[position])



    }

    override fun getItemCount(): Int = signSelectionData.size

    inner class SignSelectionViewHolder(val binding : ItemBottomsheetListBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(signSelectionData : ResponseMajorData.Data.Major){
            binding.majorData = signSelectionData

            binding.executePendingBindings()
        }
    }


}