package com.example.prick_android

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {

    // Declare variables for the email and password fields
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar : ProgressBar
    private lateinit var textView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize the views
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        registerButton = findViewById(R.id.register_button)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.loginNow)

        textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        // Set OnApplyWindowInsetsListener (already in your code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.email)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set click listener for the register button
        registerButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            // Get the email and password text
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Simple validation (can be extended as per requirements)
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Use the email and password (e.g., send to backend or show in a Toast)
                Toast.makeText(this, "Email: $email\nPassword: $password", Toast.LENGTH_SHORT).show()
                println("Email: $email\nPassword: $password")
            } else {
                // Show error if fields are empty
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                println("Please enter both email and password")
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressBar.visibility = View.GONE
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

    }
}
