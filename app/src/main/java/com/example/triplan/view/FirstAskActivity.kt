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
            var startDay=(datePickerStart.month+1).toString()+(datePickerStart.dayOfMonth).toString()
            var endDay=(datePickerEnd.month+1).toString()+(datePickerEnd.dayOfMonth).toString()
            var location= locationText.text.toString()

            Log.d("start",startDay)
            Log.d("end",endDay)
            Log.d("location",location)
            val nextIntent = Intent(this, SecondAskActivity::class.java)
            nextIntent.putExtra("start",startDay)
            nextIntent.putExtra("end",endDay)
            nextIntent.putExtra("location",location)
            startActivity(nextIntent)
        }
    }
}