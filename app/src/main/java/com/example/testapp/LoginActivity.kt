package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

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
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Login success")
                toMain()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun signupUser(username: String, password: String) {
        val user = ParseUser()
        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)
        user.signUpInBackground { e ->
            if (e == null) {
                Log.i(TAG, "Signup success")
                loginUser(username, password)
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Signup error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        val TAG = "LoginActivity"
    }
}