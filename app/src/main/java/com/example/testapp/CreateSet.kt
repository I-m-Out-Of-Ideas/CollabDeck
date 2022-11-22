package com.example.testapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class CreateSet : AppCompatActivity() {

    lateinit var setName : EditText
    lateinit var doneBtn : Button
    lateinit var backBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_set)

        setName = findViewById(R.id.id_setName)

        doneBtn = findViewById(R.id.id_doneBtn)
        backBtn = findViewById(R.id.id_backBtn)

        doneBtn.setOnClickListener {
            Log.d("ACTIVITY" , "doneBtn clicked")
            val setNameStr = setName.text.toString()
            if (setNameStr.isEmpty())
                Toast.makeText(this, "Set name is required." , Toast.LENGTH_LONG).show()
            else {
                val user = ParseUser.getCurrentUser()
                createSet(user , setNameStr)
            }
        }

        backBtn.setOnClickListener {
            finish()
        }

    }

    fun createSet(user : ParseUser , setNameStr : String) {
        val set = StudySet()
        set.addCollaborator(user)
        set.setSetName(setNameStr)
        set.saveInBackground { exception ->
            if (exception != null) {
                Log.d("ACTIVITY" , "createSet failure ${exception}")
                val toast = Toast.makeText(this , "Was not able to create study set. Please try again later.", Toast.LENGTH_LONG);
                toast.view?.setBackgroundColor(Color.RED)
                toast.show()
            } else {
                Log.d("ACTIVITY" , "createSet success")
                Toast.makeText(this , "Successfully created.", Toast.LENGTH_LONG).show()
                setName.setText("")
                val intent = Intent(this , StudySetActivity::class.java)
                intent.putExtra("set" , set)
                startActivity(intent)
            }
        }
    }

}