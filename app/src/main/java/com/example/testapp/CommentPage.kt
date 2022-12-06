package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

class CommentPage : AppCompatActivity() {

    lateinit var btnAddComment : ImageButton
    lateinit var btnBack : ImageButton
    lateinit var set : StudySet
    lateinit var flashcard: FlashCard
    lateinit var commentsRv : RecyclerView

    lateinit var adapter : CommentAdapter
    var allComments : ArrayList<Comment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_page)

        btnAddComment = findViewById(R.id.btnAddComment)
        btnBack = findViewById(R.id.btnBack)
        commentsRv = findViewById(R.id.id_commentsRV)
        set = intent.getParcelableExtra("set")!!
        flashcard = intent.getParcelableExtra("flashcard")!!

        adapter = CommentAdapter(allComments, this)
        commentsRv.adapter = adapter
        commentsRv.layoutManager = LinearLayoutManager(this)

        queryComments(flashcard)

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

    fun queryComments(flashcard: FlashCard) {
        Log.d("ACTIVITY" , "queryComments")
        val query : ParseQuery<Comment> = ParseQuery.getQuery(Comment::class.java)
        query.include(Comment.KEY_USER)
        query.include(Comment.KEY_COMMENT)
        query.whereEqualTo(Comment.KEY_FLASHCARD, flashcard);
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Comment> {
            override fun done(commentList: MutableList<Comment>, e: ParseException?) {
                if (e != null) {
                    Log.d("ACTIVITY", "comments get failure ${e}")
                } else {
                    for (comments in commentList) {
                        Log.i(
                            "ACTIVITY", "Post: " + comments.getComment() + " , username: " +
                                    comments.getUser()?.username
                        )
                    }
                    allComments.clear()
                    allComments.addAll(commentList)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == CreateComment.ADD_COMMENT_CODE) {
            Log.d("ACTIVITY" , "updated comments")
            queryComments(flashcard)
        }
    }

}