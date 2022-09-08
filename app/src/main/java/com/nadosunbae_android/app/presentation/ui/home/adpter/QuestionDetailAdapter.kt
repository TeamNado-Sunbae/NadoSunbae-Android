package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemHomeQuestionDetailBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.post.PostData

class QuestionDetailAdapter :
    ListAdapter<PostData, QuestionDetailAdapter.QuestionDetailViewHolder>(
        DiffUtilCallback<PostData>()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionDetailViewHolder {
        val binding =
            ItemHomeQuestionDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
            intent.putExtra("postId",getItem(position).postId.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    class QuestionDetailViewHolder(val binding: ItemHomeQuestionDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postData: PostData) {
            with(binding) {
                this.postData = postData
                executePendingBindings()
            }
        }
    }
}