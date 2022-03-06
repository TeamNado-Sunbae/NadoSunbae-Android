package com.nadosunbae_android.app.presentation.ui.classroom.adapter

import android.content.Context
import android.content.Intent
import android.icu.text.Transliterator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ItemInformationDetailBinding
import com.nadosunbae_android.app.presentation.ui.main.MainActivity
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.domain.model.classroom.InfoDetailData

class ClassRoomInfoDetailAdapter(private var userId: Int, val context : Context) :
    RecyclerView.Adapter<ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder>() {
    var infoDetailData = mutableListOf<InfoDetailData.Comment>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder {
        val binding = ItemInformationDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassRoomInfoDetailViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ClassRoomInfoDetailAdapter.ClassRoomInfoDetailViewHolder,
        position: Int
    ) {
        holder.onBind(infoDetailData[position])

        if (infoDetailData[position].secondMajorName == "미진입") {
            holder.binding.textInformationDetailContentSecondMajorStart.visibility = View.GONE
        }

        holder.binding.imgInformationDetailQuestionMenu.setOnClickListener {
            itemClickListener.onClick(
                it,
                position,
                lookForWriter(infoDetailData[position].writerId),
                infoDetailData[position].commentId
            )
        }
        //닉네임 클릭시 마이페이지 또는 선배 개인페이지 이동
        holder.binding.textInformationDetailCommentName.setOnClickListener {
            goMyPage(holder.itemView.context, userId, infoDetailData[position].writerId)
        }

    }

    override fun getItemCount(): Int = infoDetailData.size

    inner class ClassRoomInfoDetailViewHolder(
        val binding: ItemInformationDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(infoDetailData: InfoDetailData.Comment) {
            binding.apply {
                infoDetailListData = infoDetailData
                executePendingBindings()
            }
        }
    }

    fun setInfoDetail(infoDetailData: MutableList<InfoDetailData.Comment>) {
        this.infoDetailData = infoDetailData
        notifyDataSetChanged()
    }

    //점세개 메뉴 클릭 이벤트
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, user: Int, commentId: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener

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
        if(menuNum == 3){
            infoDetailData[position].content = context.getString(R.string.classroom_question_delete_comment)
            notifyItemChanged(position)
        }

    }

    //닉네임 클릭시 마이페이지 또는 선배 페이지 이동
    private fun goMyPage(context: Context, userId: Int, writerId: Int) {
        var fragmentNum = -1
        var bottomNavItem = -1

        if (userId == writerId) {
            fragmentNum = 6
            bottomNavItem = 4
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
        }
        ContextCompat.startActivity(context, intent, null)
    }



    companion object {
        const val writer = 1
        const val thirdParty = 2
    }

}