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
1. user presses Test button in StudySetActivity, bringing them to TestActivity.
   this should start a test session (new Leaderboard entry populated with the user and set).
2. pressing check should increment the value in the current entry.
   inside TestActivity, when querying the cards, we get an arraylist of Flashcard objects.
   we can make a second array that exists parallel to the Flashcard arraylist
   which can hold booleans with indices making them align with a particular flashcard
   so pressing Correct or Incorrect will toggle the booleans accordingly
   and navigating in the card arraylist will similarly navigate in the boolean arraylist
   which will be looped through at the end to generate a score.
   this means running score will not be visible as the user tests themself
   but i think that's ok.
3. score populates Leaderboard table which has columns for User (pointer), StudySet (pointer),
   and score (integer)
 */