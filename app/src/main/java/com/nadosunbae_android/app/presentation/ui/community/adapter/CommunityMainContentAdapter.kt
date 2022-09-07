package com.nadosunbae_android.app.presentation.ui.community.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemCommunityMainBinding
import com.nadosunbae_android.app.presentation.ui.community.CommunityDetailActivity
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
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CommunityDetailActivity::class.java)
            intent.putExtra("postId",getItem(position).postId.toString())
            holder.itemView.context.startActivity(intent)
        }
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