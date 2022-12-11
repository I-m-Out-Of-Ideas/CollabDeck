package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.children
import com.parse.ParseUser
import com.parse.SignUpCallback
import com.parse.Parse
import com.parse.ParseException


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //check if user is logged in already
        if (ParseUser.getCurrentUser() != null) {
            toMain()
        }
        //let the user log in
        findViewById<Button>(R.id.btnLogin).setOnClickListener{
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            loginUser(username, password)
        }
        //let the user sign up
        findViewById<Button>(R.id.btnSignup).setOnClickListener{
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            signupUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        progressOn()
        ParseUser.logInInBackground(username, password, ({ user, e ->
            progressOff()
            if (user != null) {
                Log.i(TAG, "Login success")
                toMain()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "User can't be logged in")
            }})
        )
    }

    private fun signupUser(username: String, password: String) {
        progressOn()
        val user = ParseUser()
        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)
        user.signUpInBackground { e ->
            progressOff()
            if (e == null) {
                Log.i(TAG, "Signup success")
                //loginUser(username, password)
                toMain()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Signup error", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "User can't create an account!")
            }
        }
    }

    private fun toMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun progressOn() {
        findViewById<ProgressBar>(R.id.id_progress).visibility = View.VISIBLE
        findViewById<RelativeLayout>(R.id.id_layout).children.iterator().forEach {
            it.isEnabled = false
            it.alpha = 0.25F
        }
    }

    fun progressOff() {
        findViewById<ProgressBar>(R.id.id_progress).visibility = View.GONE
        findViewById<RelativeLayout>(R.id.id_layout).children.iterator().forEach {
            it.isEnabled = true
            it.alpha = 1F
        }
    }

    companion object {
        val TAG = "ACTIVITY"
    }
}