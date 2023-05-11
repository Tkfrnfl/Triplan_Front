package com.example.triplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.triplan.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val gptBtn:Button=findViewById(R.id.gptBtn1)

        gptBtn.setOnClickListener{
            val nextIntent=Intent(this,GptAskActivity::class.java)
            startActivity(nextIntent)
        }
    }
}