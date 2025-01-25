package com.example.prick_android

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
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

class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar : ProgressBar
    private lateinit var registerView: View
    private lateinit var loginButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "You are currently entered with ${auth.currentUser?.email}", Toast.LENGTH_SHORT).show()
        }

        registerView = findViewById(R.id.registerNow)
        loginButton = findViewById(R.id.imageButton)

        registerView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.username)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        username = findViewById(R.id.username)

        val passwordField: EditText = findViewById(R.id.password)
        val passwordToggle: ImageView = findViewById(R.id.passwordToggle)

        var isPasswordVisible = false

        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // Show password
                passwordField.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.setImageResource(R.drawable.ic_visibility) // Icon for visible password
            } else {
                // Hide password
                passwordField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.setImageResource(R.drawable.ic_visibility_off) // Icon for hidden password
            }
            passwordField.setSelection(passwordField.text.length) // Keep cursor at the end
        }


        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(username.text.toString(), passwordField.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val usernameText = username.text.toString()
                        val passwordText = passwordField.text.toString()
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(
                            baseContext,
                            "LoggedIn with the account $usernameText and $passwordText",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        val usernameText = username.text.toString()
                        val passwordText = passwordField.text.toString()
                        Toast.makeText(
                            baseContext,
                            "Authentication failed. $usernameText and password: $passwordText",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}