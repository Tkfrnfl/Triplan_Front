package com.example.triplan.view

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GptResultActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gptres)
        val ansList: ArrayList<String>? = intent.getSerializableExtra("res") as ArrayList<String>
        val linearLayout: LinearLayout = findViewById(R.id.gptResList)


        if (ansList != null) {

            for(ans in ansList)

            {
                val textView = TextView(this)
                textView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )

                textView.text=ans
                linearLayout.addView(textView)
            }

        }
    }
}