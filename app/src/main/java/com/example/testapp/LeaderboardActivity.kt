package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class LeaderboardActivity : AppCompatActivity() {
    lateinit var lbRecyclerView: RecyclerView
    lateinit var adapter: LeaderboardAdapter
    lateinit var back : ImageButton
    var allScores: MutableList<Leaderboard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        back = findViewById(R.id.id_backBtn2)
        val set: StudySet? = intent.getParcelableExtra("set")
        lbRecyclerView = findViewById(R.id.rvLeaderboard)
        adapter = LeaderboardAdapter(this, allScores)
        lbRecyclerView.adapter = adapter
        lbRecyclerView.layoutManager = LinearLayoutManager(this)

            // back button
        back.setOnClickListener {
            val intent = Intent(this , TestActivity::class.java)
            intent.putExtra("set", set)
            startActivity(intent)
        }

        queryLeaderboard(set)
        if (set != null) {
            findViewById<TextView>(R.id.tvSetname).text = set.getSetName()
        }
    }

    fun queryLeaderboard(set: StudySet?) {
        val query: ParseQuery<Leaderboard> = ParseQuery.getQuery(Leaderboard::class.java)
        query.include(Leaderboard.KEY_USER)
        query.whereContainedIn(Leaderboard.KEY_SET, mutableListOf(set))
        query.findInBackground(object : FindCallback<Leaderboard> {
            override fun done(scores: MutableList<Leaderboard>?, e:ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching leaderboard")
                } else {
                    if (scores != null) {
                        allScores.addAll(scores)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "LeaderboardActivity"
    }

}