package com.example.triplan.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val trip = "Seoul"
        val textView: TextView = findViewById(R.id.tripView)
        textView.text = trip
    }
}