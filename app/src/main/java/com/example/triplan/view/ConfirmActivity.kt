package com.example.triplan.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.R

class ConfirmActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val intentSecondAskActivity=intent
        val res:TextView=findViewById(R.id.confirmView)
        res.text= intentSecondAskActivity.getStringExtra("res").toString()


    }
}