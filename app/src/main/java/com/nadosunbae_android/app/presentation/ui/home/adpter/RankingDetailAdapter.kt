package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
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
        holder.binding.apply {
            setVariable(BR.ranking, getItem(position))
            tvRanking.setText("${position+1}")
            when(holder.absoluteAdapterPosition) {
                0 -> {
                    ivMedal.setImageResource(R.drawable.ic_medal1)
                    tvRanking.visibility = View.INVISIBLE
                    ivMedal.visibility = View.VISIBLE
                }
                1-> {
                    ivMedal.setImageResource(R.drawable.ic_medal2)
                    tvRanking.visibility = View.INVISIBLE
                    ivMedal.visibility = View.VISIBLE
                }
                2-> {
                    ivMedal.setImageResource(R.drawable.ic_medal3)
                    tvRanking.visibility = View.INVISIBLE
                    ivMedal.visibility = View.VISIBLE
                }
                in 3..5 -> {
                    ivMedal.visibility = View.INVISIBLE
                    tvRanking.visibility = View.VISIBLE
                    tvRanking.setTextColor(Color.parseColor("#05A18F"))
                }
                else -> {
                    ivMedal.visibility = View.INVISIBLE
                    tvRanking.visibility = View.VISIBLE
                    tvRanking.setTextColor(Color.parseColor("#94959E"))
                }
            }
        }
    }

    class RankingDetailViewHolder(
        val binding: ItemHomeRankingBinding
    ) : RecyclerView.ViewHolder(binding.root)
}