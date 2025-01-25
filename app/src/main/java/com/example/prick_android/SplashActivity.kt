package com.example.prick_android

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import com.example.prick_android.Main

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val gifImageView = findViewById<ImageView>(R.id.gifImageView)

        // Load the GIF using Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.entery) // Replace with your GIF file name in the drawable folder
            .into(gifImageView)

        GlobalScope.launch {
            delay(3000) // 3 seconds delay
            val intent = Intent(this@SplashActivity, Main::class.java)
            startActivity(intent)
            finish() // Close SplashActivity so it's removed from the back stack
        }

    }
}
