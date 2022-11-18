package com.healthdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.R
import com.healthdiary.bean.TopQuestions

class TopQuestionAdapter : RecyclerView.Adapter<TopQuestionAdapter.ViewHolder>() {

    lateinit var list : List<TopQuestions.QuestionData>
    fun setResource(list : List<TopQuestions.QuestionData>){
        this.list = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_question,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data : TopQuestions.QuestionData = list[position]
        holder.tvTitle.text = data.title
        holder.tvDesc.text = data.description
        holder.cardView.setOnClickListener(View.OnClickListener {
            if (holder.tvDesc.visibility == View.VISIBLE){
                holder.tvDesc.visibility = View.GONE
                holder.ivPlus.visibility = View.GONE
                holder.ivMinus.visibility = View.VISIBLE
            }else{
                holder.tvDesc.visibility = View.VISIBLE
                holder.ivPlus.setImageResource(R.drawable.ic_minus)
                holder.ivMinus.visibility = View.GONE
                holder.ivPlus.visibility = View.VISIBLE
            }
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById<TextView>(R.id.tv_title)
        val tvDesc: TextView = itemView.findViewById<TextView>(R.id.tv_description)
        val cardView: CardView = itemView.findViewById<CardView>(R.id.cardView)
        val ivPlus : ImageView = itemView.findViewById<ImageView>(R.id.iv_plus)
        val ivMinus : ImageView = itemView.findViewById<ImageView>(R.id.iv_minus)
    }



}