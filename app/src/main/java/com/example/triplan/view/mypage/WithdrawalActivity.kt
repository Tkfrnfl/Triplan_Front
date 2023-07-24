package com.example.triplan.view.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.R
import com.example.triplan.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawal)

        val confirmBtn: Button = findViewById(R.id.confirmBtn)
        val cancelBtn: Button = findViewById(R.id.cancelBtn)

        cancelBtn.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }

        confirmBtn.setOnClickListener {

        }
    }
}