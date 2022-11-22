package com.example.testapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parse.*


class EditSet : AppCompatActivity() {

    lateinit var setName : TextView
    lateinit var edit : ImageView
    lateinit var flashCardsRV : RecyclerView
    lateinit var back : Button
    lateinit var add : FloatingActionButton

    lateinit var adapter : FlashCardsAdapter
    var flashcards : ArrayList<FlashCard> = ArrayList()
    var set : StudySet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_set)

        set = intent?.getParcelableExtra("set")
        //Log.i("ACTIVITY", "set name ${set?.getSetName()} set collab ${set?.getCollaborators()?.get(0)?.getString("username")}")

        setName = findViewById(R.id.id_setName)
        edit = findViewById(R.id.id_editBtn)
        flashCardsRV = findViewById(R.id.id_flashCardsRV)
        back = findViewById(R.id.id_backBtn)
        add = findViewById(R.id.id_addBtn)

        setName.text = set?.getSetName()

        adapter = FlashCardsAdapter(flashcards, this, true)
        flashCardsRV.adapter = adapter
        flashCardsRV.layoutManager = LinearLayoutManager(this)

        back.setOnClickListener {
            Log.d("ACTIVITY" , "back button clicked")
            val data = Intent()
            data.putExtra("name", setName.text)
            setResult(RESULT_OK, data)
            finish()
        }

        edit.setOnClickListener {
            val intent = Intent(this , EditSetName::class.java)
            intent.putExtra("set" , set)
            startActivityForResult(intent, EDIT_SET_NAME_CODE)
        }

        add.setOnClickListener {
            val intent = Intent(this , AddFlashCard::class.java)
            intent.putExtra("set" , set)
            startActivityForResult(intent, ADD_FLASH_CARD_CODE)
        }

        queryFlashCards(set)
    }

    fun queryFlashCards(set : StudySet?) {
        Log.d("ACTIVITY" , "edit queryFlashCards")
        val query : ParseQuery<FlashCard> = ParseQuery.getQuery(FlashCard::class.java)
        query.include(FlashCard.KEY_TERM)
        query.include(FlashCard.KEY_DEFINITION)
        query.whereEqualTo(FlashCard.KEY_STUDYSET, set);
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<FlashCard> {
            override fun done(cardsList: List<FlashCard>?, e: ParseException?) {
                if (e != null) {
                    Log.d("ACTIVITY", "edit flashcards get failure ${e}")
                } else {
                    Log.d("ACTIVITY", "edit flashcards get success")
                    if (cardsList != null) {
                        flashcards.clear()
                        flashcards.addAll(cardsList)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == FlashCardsAdapter.EDIT_FLASH_CARD_CODE) {
            Log.d("ACTIVITY" , "edited flashcard")
            queryFlashCards(set)
        }

        if (resultCode == RESULT_OK && requestCode == ADD_FLASH_CARD_CODE) {
            Log.d("ACTIVITY" , "added flashcard")
            queryFlashCards(set)
        }

        if (resultCode == RESULT_OK && requestCode == EDIT_SET_NAME_CODE) {
            Log.d("ACTIVITY" , "changed set name")
            setName.text = data?.getStringExtra("name")
            set?.setSetName(setName.text.toString())
        }
    }

    fun deleteFlashCard(flashCard: FlashCard) {
        Log.d("ACTIVITY" , "delete clicked for ${flashCard.getTerm()}")
        val query : ParseQuery<FlashCard> = ParseQuery.getQuery(FlashCard::class.java)
        query.whereEqualTo("objectId", flashCard.objectId)
        query.findInBackground(object : FindCallback <FlashCard> {
            override fun done(card: List<FlashCard>, e: ParseException?) {
                if (e == null) {
                    Log.d("ACTIVITY" , "delete get success")
                    card[0].deleteInBackground(object : DeleteCallback {
                        override fun done(e: ParseException?) {
                            if (e == null) {
                                Log.d("ACTIVITY" , "delete delete success")
                            } else {
                                Log.d("ACTIVITY" , "delete delete failure")
                            }
                        }
                    })
                } else {
                    Log.d("ACTIVITY" , "delete get fail")
                }
            }
        })
    }

    companion object {
        val ADD_FLASH_CARD_CODE = 2345
        val EDIT_SET_NAME_CODE = 3456
    }
}