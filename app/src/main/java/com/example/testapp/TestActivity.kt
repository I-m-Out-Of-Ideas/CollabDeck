package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

class TestActivity : AppCompatActivity() {
    lateinit var setName : TextView
    lateinit var flip : Button
    lateinit var set: StudySet
    lateinit var back : ImageButton
    lateinit var leaderBoard : ImageButton
    lateinit var comments : ImageButton
    lateinit var btnNextCard : ImageButton
    lateinit var btnLastCard : ImageButton
    lateinit var btnCorrect: ImageButton
    lateinit var btnIncorrect: ImageButton
    var flashcards : ArrayList<FlashCard> = ArrayList()
    lateinit var adapter : FlashCardsAdapter
    var n = 0
    lateinit var flashFragment: FlashcardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        setName = findViewById(R.id.id_setName)
        flip = findViewById(R.id.flip_btn)
        back = findViewById(R.id.id_backBtn)
        leaderBoard = findViewById(R.id.id_leaderBoardBtn)
        comments = findViewById(R.id.id_commentBtn)
        btnNextCard = findViewById(R.id.btnNextCard)
        btnLastCard = findViewById(R.id.btnLastCard)
        btnCorrect = findViewById(R.id.btnCorrect)
        btnIncorrect = findViewById(R.id.btnIncorrect)
        set = intent.getParcelableExtra("set")!! // get the set given from the StudySetActivity
        setName.text = set.getSetName()
        adapter = FlashCardsAdapter(flashcards, this, false)

        queryFlashCards(set)

        // go back to set (also need to tell StudySetActivity what set we were on)
        back.setOnClickListener {
            val intent = Intent(this , StudySetActivity::class.java)
            intent.putExtra("set" , set)
            startActivity(intent)
        }

        // flip the card over!
        flip.setOnClickListener {
            flashFragment.flip()
        }


        // go back to last card. If we're on the first card...don't
        btnLastCard.setOnClickListener {
            if (n == 0)
                Toast.makeText(this, "This is the first card!", Toast.LENGTH_SHORT).show()
            else {
                n--
                val args = Bundle()
                args.putString("term", flashcards[n].getTerm())
                args.putString("def", flashcards[n].getDefinition())
                flashFragment = FlashcardFragment()
                flashFragment.arguments = args
                supportFragmentManager.beginTransaction().replace(R.id.root_container, flashFragment).commit()
            }
        }

        // go to next card. If we're on the last card...don't
        btnNextCard.setOnClickListener {
            n++
            if (flashcards.size <= n) {
                Toast.makeText(this, "This is the last card!", Toast.LENGTH_SHORT).show()
                n--
            }
            else {
                val args = Bundle()
                args.putString("term", flashcards[n].getTerm())
                args.putString("def", flashcards[n].getDefinition())
                flashFragment = FlashcardFragment()
                flashFragment.arguments = args
                supportFragmentManager.beginTransaction().replace(R.id.root_container, flashFragment).commit()
            }
        }

        // mark on leaderboard as correct (NOT FUNCTIONAL YET. TOAST FOR NOW)
        btnCorrect.setOnClickListener {
            Toast.makeText(this, "Marked as correct!", Toast.LENGTH_SHORT).show()
        }
        // mark on leaderboard as incorrect (NOT FUNCTIONAL YET. TOAST FOR NOW)
        btnIncorrect.setOnClickListener {
            Toast.makeText(this, "Marked as incorrect", Toast.LENGTH_SHORT).show()
        }
        // show leaderboard (NOT FUNCTIONAL YET. TOAST FOR NOW)
        leaderBoard.setOnClickListener {
            Toast.makeText(this, "Show leaderboard!", Toast.LENGTH_SHORT).show()
        }
        // show comments (NOT FUNCTIONAL YET. TOAST FOR NOW)
        comments.setOnClickListener {
            Toast.makeText(this, "Show comments!", Toast.LENGTH_SHORT).show()
        }
    }

    fun queryFlashCards(set : StudySet?) {
        Log.d("ACTIVITY" , "queryFlashCards")
        val query : ParseQuery<FlashCard> = ParseQuery.getQuery(FlashCard::class.java)
        query.include(FlashCard.KEY_TERM)
        query.include(FlashCard.KEY_DEFINITION)
        query.whereEqualTo(FlashCard.KEY_STUDYSET, set)
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<FlashCard> {
            override fun done(cardsList: MutableList<FlashCard>, e: ParseException?) {
                if (e != null) {
                    Log.d("ACTIVITY", "flashcards get failure ${e}")
                } else {
                    Log.d("ACTIVITY", "flashcards get success")
                    //Log.d("ACTIVITY" , "cardsList ${cardsList}")
                    if (cardsList.size != 0) {
                        flashcards.clear()
                        flashcards.addAll(cardsList)
                        adapter.notifyDataSetChanged()
                        // this is how we get our terms and definitions. Easy!
                        val args = Bundle()
                        args.putString("term", flashcards[n].getTerm())
                        args.putString("def", flashcards[n].getDefinition())
                        flashFragment = FlashcardFragment()
                        flashFragment.arguments = args
                        supportFragmentManager.beginTransaction().replace(R.id.root_container, flashFragment).commit()
                    }
                    else
                        flashFragment.isVisible
                }
            }
        })
    }
}