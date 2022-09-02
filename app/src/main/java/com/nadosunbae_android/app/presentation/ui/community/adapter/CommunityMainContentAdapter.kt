package com.nadosunbae_android.app.presentation.ui.community.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemCommunityMainBinding
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.community.CommunityMainData

class CommunityMainContentAdapter :
    ListAdapter<CommunityMainData, CommunityMainContentAdapter.CommunityMainContentViewHolder>(
        DiffUtilCallback<CommunityMainData>()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityMainContentViewHolder {
        val binding =
            ItemCommunityMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityMainContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityMainContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CommunityMainContentViewHolder(val binding: ItemCommunityMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(communityMain: CommunityMainData) {
            with(binding) {
                this.communityMain = communityMain
                executePendingBindings()
            }
        }
    }
}