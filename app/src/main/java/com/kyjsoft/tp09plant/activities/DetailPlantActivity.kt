package com.kyjsoft.tp09plant.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kyjsoft.tp09plant.databinding.ActivityDetailPlantBinding

class DetailPlantActivity : AppCompatActivity() {

    val binding by lazy { ActivityDetailPlantBinding.inflate(layoutInflater) }
    lateinit var imgUrl : String
    lateinit var title : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            val intent = Intent(this, SavePlantActivity::class.java)
            intent.putExtra("plantImg", imgUrl)
            intent.putExtra("plantName", title)
            startActivity(intent)
        }



        binding.tvTitle.text = intent.getStringExtra("title")
        Glide.with(this).load(intent.getStringExtra("imgUrl")).into(binding.ivDetail)
        imgUrl = intent.getStringExtra("imgUrl").toString()
        title = intent.getStringExtra("title").toString()


    }
}