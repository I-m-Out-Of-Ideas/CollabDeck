package com.example.testapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class LeaderboardAdapter(val context: Context, val leaderboards: List<Leaderboard>) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.score, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val score = leaderboards.get(position)
        holder.bind(score)
    }

    override fun getItemCount(): Int {
        return leaderboards.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfile: ImageView
        val scoreUname: TextView
        val tvScore: TextView

        init {
            ivProfile = itemView.findViewById(R.id.ivProfile)
            scoreUname = itemView.findViewById(R.id.scoreUname)
            tvScore = itemView.findViewById(R.id.tvScore)
        }

        fun bind(leaderboard: Leaderboard) {
            tvScore.text = leaderboard.getScore().toString() + "/" + leaderboard.getTotal().toString()
            scoreUname.text = leaderboard.getUser()?.username
            Glide.with(itemView.context).load(leaderboard.getUser()?.getParseFile("pfp")?.url).placeholder(R.drawable.loading).thumbnail(Glide.with(itemView.context).load(R.drawable.loading)).apply(
                RequestOptions.circleCropTransform()).into(ivProfile)
        }
    }

}