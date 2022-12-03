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
import com.parse.ParseUser

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
    var cardsCorrect : ArrayList<Boolean> = ArrayList()
    lateinit var adapter : FlashCardsAdapter
    var n = 0
    var score = 0
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

        // mark card as correct
        btnCorrect.setOnClickListener {
            cardsCorrect[n] = true
        }
        // mark card as incorrect
        btnIncorrect.setOnClickListener {
            cardsCorrect[n] = false
        }
        // save score and show leaderboard (going to the leaderboard page confirms your score and posts it to the leaderboard)
        leaderBoard.setOnClickListener {
            postScore()
            val intent = Intent(this , LeaderboardActivity::class.java)
            startActivity(intent)
        }
        // save score and show comments (going to the comments page confirms your score and posts it to the leaderboard)
        comments.setOnClickListener {
            postScore()
            val intent = Intent(this , CommentPage::class.java)
            intent.putExtra("flashcard" , flashcards[n])
            intent.putExtra("set", set)
            startActivity(intent)
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
                        for(card in flashcards) {   //populate the table with as many booleans as there are cards
                            cardsCorrect.add(false) //mark as wrong by default. catch this L bestie
                        }
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

    fun postScore() {
        for (bool in cardsCorrect){ //collect up total correct
            if (bool) {
                score++
            }
        }
        val leaderboardObj = Leaderboard()
        leaderboardObj.setUser(ParseUser.getCurrentUser())
        leaderboardObj.setSet(set)
        leaderboardObj.setScore(score)
        leaderboardObj.setTotal(flashcards.size)
        leaderboardObj.saveInBackground { exception ->
            if (exception != null) {
                // Something went wrong
                Log.e("TestActivity", "Error while saving score")
                exception.printStackTrace()
                Toast.makeText(this, "Error saving score!",
                    Toast.LENGTH_SHORT).show()
            } else {
                Log.i("TestActivity", "Successfully saved score!")
            }
        }
    }
}