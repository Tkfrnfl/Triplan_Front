package com.example.triplan.view

import android.content.Intent
import android.net.Uri
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
        val loginBtn:Button=findViewById(R.id.loginBtn1)

        gptBtn.setOnClickListener{
            val nextIntent=Intent(this,GptAskActivity::class.java)
            startActivity(nextIntent)
        }

        loginBtn.setOnClickListener{
            val url="https://kauth.kakao.com/oauth/authorize?client_id=aa2c572bd90a086842d6c97ea2985589&redirect_uri=http://localhost:8080/user/login/kakao-callback&response_type=code"
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}