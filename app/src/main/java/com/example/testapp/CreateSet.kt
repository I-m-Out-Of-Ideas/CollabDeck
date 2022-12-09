package com.example.testapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.children
import com.parse.ParseUser

class CreateSet : AppCompatActivity() {

    lateinit var setName : EditText
    lateinit var doneBtn : Button
    lateinit var progress : ProgressBar
    lateinit var backBtn : Button
    lateinit var layout : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_set)

        setName = findViewById(R.id.id_setName)

        doneBtn = findViewById(R.id.id_doneBtn)
        progress = findViewById(R.id.id_progress)
        backBtn = findViewById(R.id.id_backBtn)
        layout = findViewById(R.id.id_layout)

        doneBtn.setOnClickListener {
            Log.d("ACTIVITY" , "doneBtn clicked")
            progressOn()
            val setNameStr = setName.text.toString()
            if (setNameStr.isEmpty()) {
                Toast.makeText(this, "Set name is required.", Toast.LENGTH_LONG).show()
                progressOff()
            }
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
            progressOff()
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

    fun progressOn() {
        progress.visibility = View.VISIBLE
        layout.children.iterator().forEach {
            it.isEnabled = false
            it.alpha = 0.25F
        }
    }

    fun progressOff() {
        progress.visibility = View.GONE
        layout.children.iterator().forEach {
            it.isEnabled = true
            it.alpha = 1F
        }
    }

}