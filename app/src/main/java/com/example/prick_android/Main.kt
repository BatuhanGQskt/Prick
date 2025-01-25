package com.example.prick_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Main : AppCompatActivity() {
    private lateinit var signOut: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val imageView: ImageView = findViewById(R.id.imageView)
        signOut = findViewById(R.id.signOut)

        signOut.setOnClickListener {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                Toast.makeText(
                    this,
                    "You are signed out from ${currentUser.email}",
                    Toast.LENGTH_SHORT
                ).show()
                Firebase.auth.signOut()
            } else {
                Toast.makeText(
                    this,
                    "No, current user please login!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Login button functionality
        btnLogin.setOnClickListener {
            // Add your login logic here
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Register button functionality
        btnRegister.setOnClickListener {
            // Navigate to a registration activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
