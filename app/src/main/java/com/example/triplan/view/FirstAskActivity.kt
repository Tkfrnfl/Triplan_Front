package com.example.triplan.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
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
        val locationText:EditText=findViewById(R.id.textLocation)

        nextBtn.setOnClickListener {
            val startDay = String.format("%04d-%02d-%02d",datePickerStart.year,datePickerStart.month+1,datePickerStart.dayOfMonth)
            val endDay = String.format("%04d-%02d-%02d",datePickerEnd.year, datePickerEnd.month+1, datePickerEnd.dayOfMonth)
            val location= locationText.text.toString()
            Log.d("start",startDay)
            Log.d("end",endDay)
            Log.d("location",location)
            val nextIntent = Intent(this, SecondAskActivity::class.java)
            nextIntent.putExtra("start",startDay)
            nextIntent.putExtra("end",endDay)
            nextIntent.putExtra("location",location)
            val days= endDay.split("-")[2].toInt() - startDay.split("-")[2].toInt()
            nextIntent.putExtra("days",days)
            nextIntent.putExtra("NoD",days)
            startActivity(nextIntent)
        }
    }
}