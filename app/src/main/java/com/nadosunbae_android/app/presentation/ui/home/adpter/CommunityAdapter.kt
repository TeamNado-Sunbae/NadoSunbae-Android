package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemHomeCommunityPostBinding
import com.nadosunbae_android.app.databinding.ItemHomeQuestionBinding
import com.nadosunbae_android.app.databinding.ItemHomeReviewBinding
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.home.HomeCommunityData
import com.nadosunbae_android.domain.model.home.HomeQuestionData
import com.nadosunbae_android.domain.model.home.HomeReviewData

class CommunityAdapter :  androidx.recyclerview.widget.ListAdapter<HomeCommunityData, CommunityAdapter.CommunityViewHolder>(
    DiffUtilCallback<HomeCommunityData>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val binding = ItemHomeCommunityPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommunityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.binding.setVariable(BR.Community, getItem(position))
    }


    class CommunityViewHolder(
        val binding: ItemHomeCommunityPostBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}