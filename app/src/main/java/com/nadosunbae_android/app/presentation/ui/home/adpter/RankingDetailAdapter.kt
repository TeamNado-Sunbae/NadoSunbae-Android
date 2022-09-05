package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemHomeRankingBinding
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.home.HomeRankingData
import com.nadosunbae_android.domain.model.home.RankingTest

class RankingDetailAdapter() :
    androidx.recyclerview.widget.ListAdapter<HomeRankingData, RankingDetailAdapter.RankingDetailViewHolder>(
        DiffUtilCallback<HomeRankingData>()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingDetailViewHolder {
        val binding = ItemHomeRankingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RankingDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingDetailViewHolder, position: Int) {
        holder.binding.setVariable(BR.ranking, getItem(position))
    }

    class RankingDetailViewHolder(
        val binding: ItemHomeRankingBinding
    ) : RecyclerView.ViewHolder(binding.root)
}