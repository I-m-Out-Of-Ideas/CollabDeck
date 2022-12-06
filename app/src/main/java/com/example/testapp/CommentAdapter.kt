package com.example.testapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter (private var comments : ArrayList<Comment>,  private val context : Context)
    : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comments, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvComment: TextView
        val tvCommenter: TextView

        init {
            tvComment = itemView.findViewById(R.id.id_comment)
            tvCommenter = itemView.findViewById(R.id.id_commenter)
            //tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt)
        }

        fun bind(comment: Comment) {
            tvComment.text = comment.getComment()
            tvCommenter.text = comment.getUser()?.username
            //tvCreatedAt.text = comment.getFormattedTimestamp(comment.createdAt)

        }
    }
}

