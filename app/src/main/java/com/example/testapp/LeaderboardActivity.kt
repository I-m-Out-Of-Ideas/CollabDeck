package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LeaderboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
    }
}
/*
2. the boolean arraylist will be looped through at the end to generate a score.
   this means running score will not be visible as the user tests themself
   but i think that's ok.
3. score populates Leaderboard table which has columns for User (pointer), StudySet (pointer),
   and score (integer)
4. Leaderboard table is used to populate LeaderboardActivity page (RecyclerView)
 */