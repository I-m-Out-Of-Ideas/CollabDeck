package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.parse.ParseUser

class CreateComment : AppCompatActivity() {

    lateinit var btnCancel : ImageButton
    lateinit var btnPostComment : ImageButton
    lateinit var set : StudySet
    lateinit var flashcard: FlashCard
    lateinit var user : ParseUser
    lateinit var comment : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_comment)

        btnCancel = findViewById(R.id.btnCancel)
        btnPostComment = findViewById(R.id.btnPostComment)
        set = intent.getParcelableExtra("set")!!
        flashcard = intent.getParcelableExtra("flashcard")!!
        user = ParseUser.getCurrentUser()

        btnCancel.setOnClickListener {
            val intent = Intent(this , CommentPage::class.java)
            intent.putExtra("set", set)
            intent.putExtra("flashcard", flashcard)
            startActivity(intent)
        }

        btnPostComment.setOnClickListener {
            comment = findViewById<EditText>(R.id.commentText).text.toString()
            postComment(user, flashcard, comment)
            val intent = Intent(this , CommentPage::class.java)
            intent.putExtra("set", set)
            intent.putExtra("flashcard", flashcard)
            startActivityForResult(intent, ADD_COMMENT_CODE)
        }
    }

    fun postComment(user: ParseUser, flashcard: FlashCard, comment: String) {
        val commentObj = Comment()
        commentObj.setUser(user)
        commentObj.setFlashcard(flashcard)
        commentObj.setComment(comment)
        commentObj.saveInBackground { exception ->
            if (exception != null) {
                // Something went wrong
                Log.e(TAG, "Error while saving post")
                exception.printStackTrace()
                Toast.makeText(this, "Error saving post!",
                    Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "Successfully saved post!")
            }
        }
    }

    companion object {
        const val TAG = "CreateComment"
        val ADD_COMMENT_CODE = 8080
    }
}