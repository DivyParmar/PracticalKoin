package com.divy.practicalkoin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.divy.practicalkoin.databinding.RsQuestionsBinding


class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private var questions: ArrayList<String> = ArrayList()
    private lateinit var mcontext: Context

    class ViewHolder(val binding: RsQuestionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mcontext = parent.context
        return ViewHolder(
            RsQuestionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        with(holder.binding)
        {
            holder.setIsRecyclable(false)
            val question = questions[position]

        }
    }

    override fun getItemCount() = questions.size

    fun clear() {
        questions.clear()
        notifyDataSetChanged()
    }

    fun addList(list: ArrayList<String>?) {
        if (list != null) {
            this.questions.addAll(list)
        }
        notifyDataSetChanged()
    }


    fun addItem(question: String) {
        this.questions.add(question)
        notifyItemInserted(questions.size - 1)
    }


    fun getQuestionsList(): ArrayList<String> {
        return questions
    }
}