package com.kyjsoft.tp09plant.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kyjsoft.tp09plant.R
import java.lang.Runnable
import android.content.Intent
import android.os.Handler
import com.kyjsoft.tp09plant.activities.MainActivity
import com.kyjsoft.tp09plant.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}