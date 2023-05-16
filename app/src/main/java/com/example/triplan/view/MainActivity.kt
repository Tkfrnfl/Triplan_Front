package com.example.triplan.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.triplan.R
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gptBtn:Button=findViewById(R.id.gptBtn1)

        val askBtn:Button=findViewById(R.id.askBtn)
        val loginBtn:Button=findViewById(R.id.loginBtn)

        gptBtn.setOnClickListener{
            val nextIntent= Intent(this,GptAskActivity::class.java)
            startActivity(nextIntent)
        }



        loginBtn.setOnClickListener{
            val nextIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextIntent)
        }


        askBtn.setOnClickListener {
            val intent=Intent(this,FirstAskActivity::class.java)
            startActivity(intent)
        }

    }
}