package com.example.testapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddFlashCard : AppCompatActivity() {
    private lateinit var term : EditText
    private lateinit var definition : EditText
    private lateinit var add : Button
    private lateinit var cancel : Button

    lateinit var set : StudySet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_flash_card)

        set = intent.getParcelableExtra("set")!!

        term = findViewById(R.id.id_term)
        definition = findViewById(R.id.id_definition)
        add = findViewById(R.id.id_saveBtn)
        add.setText("add")
        cancel = findViewById(R.id.id_cancelBtn)

        add.setOnClickListener {
            Log.d("ACTIVITY" , "save button clicked")
            if (term.text.isEmpty() || definition.text.isEmpty()) {
                val toast = Toast.makeText(this , "Term or definition cannot be empty", Toast.LENGTH_LONG);
                toast.view?.setBackgroundColor(Color.RED)
                toast.show()
            }
            else {
                val flashCard = FlashCard()
                flashCard.setSet(set)
                flashCard.setTerm(term.text.toString())
                flashCard.setDefinition(definition.text.toString())
                flashCard.saveInBackground { exception ->
                    if (exception != null) {
                        Log.d("ACTIVITY", "add failure ${exception}")
                    } else {
                        Log.d("ACTIVITY", "add success")
                        setResult(RESULT_OK)
                        finish()
                    }
                }
            }
        }

        cancel.setOnClickListener {
            onBackPressed()
        }

    }
}