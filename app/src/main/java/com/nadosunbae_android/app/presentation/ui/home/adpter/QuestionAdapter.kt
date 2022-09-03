package com.nadosunbae_android.app.presentation.ui.home.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.app.databinding.ItemHomeQuestionBinding
import com.nadosunbae_android.app.util.DiffUtilCallback
import com.nadosunbae_android.domain.model.home.HomeQuestionData

class QuestionAdapter :  androidx.recyclerview.widget.ListAdapter<HomeQuestionData, QuestionAdapter.QuestionViewHolder>(
    DiffUtilCallback<HomeQuestionData>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemHomeQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.binding.setVariable(BR.Question, getItem(position))
    }


    class QuestionViewHolder(
        val binding: ItemHomeQuestionBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}