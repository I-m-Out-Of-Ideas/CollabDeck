package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {

    lateinit var countInfo : RelativeLayout
    lateinit var setsCount : TextView
    lateinit var count : TextView
    lateinit var studySetsRV : RecyclerView
    lateinit var progress : ProgressBar
    lateinit var createSet : Button

    lateinit var adapter : StudySetsAdapter
    var studySets : ArrayList<StudySet> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countInfo = findViewById(R.id.id_countInfo)
        setsCount = findViewById(R.id.id_setsCount)
        count = findViewById(R.id.id_count)
        studySetsRV = findViewById(R.id.id_studySetsRV)
        progress = findViewById(R.id.id_progress)
        createSet = findViewById(R.id.id_createSetBtn)

        adapter = StudySetsAdapter(studySets, this)
        studySetsRV.adapter = adapter
        studySetsRV.layoutManager = LinearLayoutManager(this)

        queryStudySets()

        // takes user to logout confirmation screen
        findViewById<Button>(R.id.logout_button).setOnClickListener {
            val intent = Intent(this@MainActivity, logoutConfirmationActivity::class.java)
            startActivity(intent)
        }

        createSet.setOnClickListener {
            val intent = Intent(this, CreateSet::class.java)
            startActivity(intent)
        }

    }

    fun queryStudySets() {
        Log.d("ACTIVITY" , "query studySets")
        progress.visibility = View.VISIBLE
        countInfo.visibility = View.GONE
        val query : ParseQuery<StudySet> = ParseQuery.getQuery(StudySet::class.java)
        query.whereContainedIn(StudySet.KEY_COLLABORATOR, mutableListOf(ParseUser.getCurrentUser()))
        query.findInBackground(object : FindCallback<StudySet> {
            override fun done(setList: MutableList<StudySet>?, e: ParseException?) {
                progress.visibility = View.GONE
                if (e != null) {
                    Log.d("ACTIVITY", "studysets get failure ${e}")
                } else {
                    Log.d("ACTIVITY", "studysets get success")
                    //Log.d("ACTIVITY" , "setList ${setList}")
                    countInfo.visibility = View.VISIBLE
                    setsCount.text = setList?.size.toString()
                    if (setList != null) {
                        studySets.clear()
                        studySets.addAll(setList)
                        adapter.notifyDataSetChanged()
                        if (studySets.size == 1)
                            count.text = " set"
                        else
                            count.text = " sets"
                    }
                    else
                        Log.d("ACTIVITY", "studysets empty")
                }
            }
        })
        // take user to profile screen
        findViewById<Button>(R.id.profile_button).setOnClickListener {
            val intent = Intent(this@MainActivity, profile_activity::class.java)
            startActivity(intent)
        }

        // take user to settings screen
        findViewById<Button>(R.id.settings_button).setOnClickListener {
            val intent = Intent(this@MainActivity, settingsActivity::class.java)
            startActivity(intent)
        }

    }
}