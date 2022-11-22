package com.example.testapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.GetCallback
import com.parse.ParseException
import com.parse.ParseQuery

class EditFlashCard : AppCompatActivity() {

    private lateinit var term : EditText
    private lateinit var definition : EditText
    private lateinit var save : Button
    private lateinit var cancel : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_flash_card)

        term = findViewById(R.id.id_term)
        definition = findViewById(R.id.id_definition)
        save = findViewById(R.id.id_saveBtn)
        cancel = findViewById(R.id.id_cancelBtn)

        val flashCard: FlashCard? = intent.getParcelableExtra("flashcard")
        //Log.d("ACTIVITY" , "name ${flashCard?.getTerm()}")

        term.setText(flashCard?.getTerm())
        definition.setText(flashCard?.getDefinition())

        save.setOnClickListener {
            Log.d("ACTIVITY" , "save button clicked")
            val query : ParseQuery<FlashCard> = ParseQuery.getQuery(FlashCard::class.java)
            query.getInBackground(flashCard?.objectId, object : GetCallback<FlashCard> {
                override fun done(card: FlashCard?, e: ParseException?) {
                    if (e == null) {
                        Log.d("ACTIVITY" , "get flashcard success")
                        if (term.text.isEmpty() || definition.text.isEmpty()) {
                            val toast = Toast.makeText(this@EditFlashCard, "term or definition cannot be empty", Toast.LENGTH_SHORT)
                            toast.view?.setBackgroundColor(Color.RED)
                            toast.show()
                        }
                        else {
                            card?.setTerm(term.text.toString())
                            card?.setDefinition(definition.text.toString())
                            card?.saveInBackground {
                                if (it == null) {
                                    Log.d("ACTIVITY", "save flashcard success")
                                    setResult(RESULT_OK)
                                    finish()
                                } else {
                                    Log.d("ACTIVITY", "save flashcard failure")
                                }
                            }
                        }
                    }
                    else {
                        Log.d("ACTIVITY" , "get flashcard fail")
                    }
                }
            })
        }

        cancel.setOnClickListener {
            finish()
        }

    }

}