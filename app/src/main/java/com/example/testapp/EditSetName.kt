package com.example.testapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.GetCallback
import com.parse.ParseException
import com.parse.ParseQuery

class EditSetName : AppCompatActivity() {

    lateinit var setName : EditText
    lateinit var save : Button
    lateinit var cancel : Button

    var set : StudySet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_set_name)

        set = intent?.getParcelableExtra("set")
        //Log.i("ACTIVITY", "set name ${set?.getSetName()} set collab ${set?.getCollaborators()?.get(0)?.getString("username")}")

        setName = findViewById(R.id.id_setName)
        save = findViewById(R.id.id_saveBtn)
        cancel = findViewById(R.id.id_cancelBtn)

        setName.setText(set?.getSetName())

        save.setOnClickListener {
            Log.d("ACTIVITY" , "save button clicked")
            val query : ParseQuery<StudySet> = ParseQuery.getQuery(StudySet::class.java)
            query.getInBackground(set?.objectId, object : GetCallback<StudySet> {
                override fun done(studySet: StudySet?, e: ParseException?) {
                    if (e == null) {
                        Log.d("ACTIVITY" , "get studyset success")
                        if (setName.text.isEmpty()) {
                            val toast = Toast.makeText(this@EditSetName, "Set title cannot be empty", Toast.LENGTH_SHORT)
                            toast.view?.setBackgroundColor(Color.RED)
                            toast.show()
                        }
                        else {
                            studySet?.setSetName(setName.text.toString())
                            studySet?.saveInBackground {
                                if (it == null) {
                                    Log.d("ACTIVITY", "save studyset success")
                                    val data = Intent()
                                    data.putExtra("name", studySet.getSetName())
                                    setResult(RESULT_OK, data)
                                    finish()
                                } else {
                                    Log.d("ACTIVITY", "save studyset failure")
                                }
                            }
                        }
                    }
                    else {
                        Log.d("ACTIVITY" , "get studyset fail")
                    }
                }
            })
        }

        cancel.setOnClickListener {
            finish()
        }

    }
}