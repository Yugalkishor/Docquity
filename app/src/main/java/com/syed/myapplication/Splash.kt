package com.syed.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this).asGif().load(R.drawable.welcome).into(iv_splash)
        lifecycleScope.launch {
            delay(3000L)
            val intent = Intent(this@Splash, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        }
}