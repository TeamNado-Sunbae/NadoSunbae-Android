package com.nadosunbae_android.app.presentation.ui.community.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemCommunityDetailBinding
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.post.PostDetailData

class CommunityPostDetailAdapter(private var userId: Int, val context: Context) :
    ListAdapter<PostDetailData.Comment, CommunityPostDetailAdapter.ClassRoomInfoDetailViewHolder>(
        DiffUtilCallback<PostDetailData.Comment>()
    ) {
    private var onItemCLickListener: ((View, Int, Int, Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomInfoDetailViewHolder {
        val binding = ItemCommunityDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomInfoDetailViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomInfoDetailViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))

        if (getItem(position).secondMajorName == "미진입") {
            holder.binding.textInformationDetailContentSecondMajorStart.visibility = View.GONE
        }

        holder.binding.imgInformationDetailQuestionMenu.setOnClickListener { view ->
            onItemCLickListener?.let {
                it(
                    view, position, lookForWriter(getItem(position).commentWriterId),
                    getItem(position).commentId
                )
            }
        }
        //닉네임 클릭시 마이페이지 또는 선배 개인페이지 이동
        holder.binding.textInformationDetailCommentName.setOnClickListener {
            goMyPage(holder.itemView.context, userId, getItem(position).commentWriterId)
        }
        holder.binding.imgInformationDetailCommentProfile.setOnClickListener {
            goMyPage(holder.itemView.context, userId, getItem(position).commentWriterId)
        }

    }

    class ClassRoomInfoDetailViewHolder(
        val binding: ItemCommunityDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postDetailData: PostDetailData.Comment) {
            binding.apply {
                this.postDetailCommentData = postDetailData
                executePendingBindings()
            }
        }
    }

    //점세개 메뉴 클릭 이벤트
    fun setItemClickListener(listener: (View, Int, Int, Int) -> Unit) {
        this.onItemCLickListener = listener
    }


    // 삭제, 신고를 위한 user 구분 (작성자 -> 1, 제3자 -> 2)
    private fun lookForWriter(writerId: Int): Int {
        return if (userId == writerId) {
            writer
        } else {
            thirdParty
        }

    }

    //삭제시에 어떤 아이템인지 받아오는 부분
    // update = 1, report = 2, delete = 3 (menuNum)

    fun setCheckMenu(menuNum: Int, position: Int) {
        if (menuNum == 3) {
            getItem(position).content =
                context.getString(R.string.classroom_question_delete_comment)
            getItem(position).isDeleted = true
            notifyItemChanged(position)
        }

    }

    //닉네임 클릭시 마이페이지 또는 선배 페이지 이동
    private fun goMyPage(context: Context, userId: Int, writerId: Int) {
        var fragmentNum = -1
        var bottomNavItem = -1

        if (userId == writerId) {
            fragmentNum = 6
            bottomNavItem = 5
        } else {
            fragmentNum = 4
            bottomNavItem = 2
        }
        val intent = Intent(context, MainActivity::class.java)
        intent.apply {
            putExtra("fragmentNum", fragmentNum)
            putExtra("bottomNavItem", bottomNavItem)
            putExtra("signData", MainGlobals.signInData)
            putExtra("loading", false)
            putExtra("seniorId", writerId)
            putExtra("blockDivision", 1)
        }
        ContextCompat.startActivity(context, intent, null)
    }


    companion object {
        const val writer = 1
        const val thirdParty = 2
    }

}