package com.example.triplan.view

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstAskActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)

        val datePickerStart=findViewById<DatePicker>(R.id.datePicker1)
        val datePickerEnd=findViewById<DatePicker>(R.id.datePicker2)
        val nextBtn: Button =findViewById(R.id.askNextBtn)

        nextBtn.setOnClickListener {
            Log.d("start",(datePickerStart.month+1).toString()+(datePickerStart.dayOfMonth).toString())
            Log.d("end",(datePickerEnd.month+1).toString()+(datePickerEnd.dayOfMonth).toString())

        }
    }
}