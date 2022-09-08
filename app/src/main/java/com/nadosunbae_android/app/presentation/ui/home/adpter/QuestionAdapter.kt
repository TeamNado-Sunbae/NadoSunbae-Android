package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemHomeQuestionBinding
import com.nadosunbae_android.app.presentation.ui.classroom.QuestionDetailActivity
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.post.PostData

class QuestionAdapter :
    ListAdapter<PostData, QuestionAdapter.QuestionViewHolder>(
        DiffUtilCallback<PostData>()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionViewHolder {
        val binding =
            ItemHomeQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, QuestionDetailActivity::class.java)
            intent.putExtra("postId",getItem(position).postId.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    class QuestionViewHolder(val binding: ItemHomeQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postData: PostData) {
            with(binding) {
                this.postData = postData
                executePendingBindings()
            }
        }
    }
}