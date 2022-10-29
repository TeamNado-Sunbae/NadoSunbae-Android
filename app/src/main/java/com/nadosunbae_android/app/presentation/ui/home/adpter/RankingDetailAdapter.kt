package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemHomeRankingBinding
import com.nadosunbae_android.app.presentation.ui.classroom.question.DataToFragment
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityPostDetailAdapter
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.home.HomeRankingData
import com.nadosunbae_android.domain.model.home.RankingTest
import timber.log.Timber

class RankingDetailAdapter(private var userId: Int, var link : DataToFragment,val seniorRanking : Boolean?) :
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

    private var onItemCLickListener: ((View, Int, Int, Int) -> Unit)? = null

    // 삭제, 신고를 위한 user 구분 (작성자 -> 1, 제3자 -> 2)
    private fun lookForWriter(writerId: Int): Int {
        return if (userId == writerId) {
            CommunityPostDetailAdapter.writer
        } else {
            CommunityPostDetailAdapter.thirdParty
        }

    }

    //TODO: 선배 프로필 페이지로 이동
    override fun onBindViewHolder(holder: RankingDetailViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.ranking, getItem(position))
            if (getItem(position).secondMajorName == "미진입" || getItem(position).secondMajorName == "") {
                holder.binding.textSecondMajorTime.visibility = View.GONE
            }
            holder.itemView.setOnClickListener { view ->
                val paramVal = if(seniorRanking == true){
                    "senior_ranking_toggleon"
                }else{
                    "senior_ranking_in"
                }
                FirebaseAnalyticsUtil.firebaseLog("senior_click","journey",paramVal)
                link.getSeniorId(getItem(position).id)
                onItemCLickListener?.let {
                    //link.getSeniorId(getItem(position).id)
                    /*
                    it(
                        view, position, lookForWriter(getItem(position).id),
                        getItem(position).id
                    )
                     */
                }
            }
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
                in 3..4 -> {
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