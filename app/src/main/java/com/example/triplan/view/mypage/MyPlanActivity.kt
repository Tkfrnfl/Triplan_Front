package com.example.triplan.view.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.R
import com.example.triplan.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlanActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myplan)

        val planBtn: Button = findViewById(R.id.planBtn)
        val scrapBtn: Button = findViewById(R.id.scrapBtn)
        val homeBtn: Button = findViewById(R.id.homeBtn)

        planBtn.setOnClickListener {
            val nextIntent = Intent(this, MyPlanActivity::class.java)
            startActivity(nextIntent)
        }

        scrapBtn.setOnClickListener {
            val nextIntent = Intent(this, MyScrapActivity::class.java)
            startActivity(nextIntent)
        }

        homeBtn.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
    }
}