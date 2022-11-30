package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class CommentPage : AppCompatActivity() {

    lateinit var btnAddComment : ImageButton
    lateinit var btnBack : ImageButton
    lateinit var set : StudySet
    lateinit var flashcard: FlashCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_page)

        btnAddComment = findViewById(R.id.btnAddComment)
        btnBack = findViewById(R.id.btnBack)
        set = intent.getParcelableExtra("set")!!
        flashcard = intent.getParcelableExtra("flashcard")!!

        btnAddComment.setOnClickListener {
            val intent = Intent(this , CreateComment::class.java)
            intent.putExtra("set", set)
            intent.putExtra("flashcard", flashcard)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("set", set)
            startActivity(intent)
        }
    }


}