package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.FindCallback
import com.parse.GetCallback
import com.parse.ParseException
import com.parse.ParseQuery


class StudySetActivity : AppCompatActivity() {

    lateinit var setName : TextView
    lateinit var username : TextView
    lateinit var termsCount : TextView
    lateinit var count : TextView
    lateinit var terms : TextView
    lateinit var flashcardsRV : RecyclerView

    lateinit var back : ImageButton
    lateinit var leaderBoard : ImageButton
    lateinit var test : ImageButton
    lateinit var edit : ImageButton
    lateinit var collaborators : ImageButton


    lateinit var adapter : FlashCardsAdapter
    var flashcards : ArrayList<FlashCard> = ArrayList()
    lateinit var set: StudySet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_set)

        setName = findViewById(R.id.id_setName)
        username = findViewById(R.id.id_userName)
        termsCount = findViewById(R.id.id_termsCount)
        count = findViewById(R.id.id_count)
        terms = findViewById(R.id.id_terms)
        flashcardsRV = findViewById(R.id.id_flashCardsRV)
        back = findViewById(R.id.id_backBtn)
        leaderBoard = findViewById(R.id.id_leaderBoardBtn)
        test = findViewById(R.id.id_testBtn)
        edit = findViewById(R.id.id_editBtn)
        collaborators = findViewById(R.id.id_collaboratorsBtn)

        set = intent.getParcelableExtra("set")!!
        setName.text = set?.getSetName()
        username.text = set?.getCollaborators()?.get(0)?.fetchIfNeeded()?.username

        adapter = FlashCardsAdapter(flashcards, this, false)
        flashcardsRV.adapter = adapter
        flashcardsRV.layoutManager = LinearLayoutManager(this)

        //Log.i("ACTIVITY", "activity set name ${set?.getSetName()}")
        //Log.i("ACTIVITY", "activity collab ${set?.getCollaborators()?.get(0)?.fetchIfNeeded()?.username}")

        queryFlashCards(set)

        back.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
        }

        edit.setOnClickListener{
            val intent = Intent(this , EditSet::class.java)
            intent.putExtra("set" , set)
            startActivityForResult(intent, EDIT_SET_CODE)
        }
        test.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("set", set)
            startActivity(intent)
        }
    }

    fun querySetCount(cardsList: MutableList<FlashCard>) {
        termsCount.text = cardsList.size.toString()
        //Log.d("RITIKA" , "seize ${cardsList.size}")
        if (cardsList.size == 1)
            count.text = " term"
        else
            count.text = " terms"
    }

    fun queryFlashCards(set : StudySet?) {
        Log.d("ACTIVITY" , "queryFlashCards")
        val query : ParseQuery<FlashCard> = ParseQuery.getQuery(FlashCard::class.java)
        query.include(FlashCard.KEY_TERM)
        query.include(FlashCard.KEY_DEFINITION)
        query.whereEqualTo(FlashCard.KEY_STUDYSET, set);
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<FlashCard> {
            override fun done(cardsList: MutableList<FlashCard>, e: ParseException?) {
                if (e != null) {
                    Log.d("ACTIVITY", "flashcards get failure ${e}")
                } else {
                    Log.d("ACTIVITY", "flashcards get success")
                    Log.d("ACTIVITY" , "cardsList ${cardsList}")
                    querySetCount(cardsList)
                    if (cardsList.size != 0) {
                        flashcards.clear()
                        flashcards.addAll(cardsList)
                        adapter.notifyDataSetChanged()
                    }
                    else
                        terms.visibility = View.GONE
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == EDIT_SET_CODE) {
            Log.d("ACTIVITY" , "updated study set")
            setName.text = data?.getStringExtra("name")
            queryFlashCards(set)
        }
    }
    companion object {
        val EDIT_SET_CODE = 1234
    }

}