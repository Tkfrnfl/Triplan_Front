package com.example.triplan.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondAskActivity: AppCompatActivity() {

    private val tripPlaces = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_2)

        val nextBtn: Button = findViewById(R.id.secondAskNextBtn)
        val linearLayout: LinearLayout = findViewById(R.id.tripPlace)
        val addBtn: Button = findViewById(R.id.addButton)

        addBtn.setOnClickListener {
            val newEditText = EditText(this)
            newEditText.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            // addButton을 부모 뷰그룹에서 제거
            linearLayout.removeView(addBtn)

            // 새로운 EditText 추가
            linearLayout.addView(newEditText)
            tripPlaces.add(newEditText) // 리스트에 EditText 추가

            // addButton 다시 추가
            linearLayout.addView(addBtn)
        }

        nextBtn.setOnClickListener {
            val nextIntent = Intent(this, SecondAskActivity::class.java)
            tripPlaces.forEach { places ->
                val place = places.text.toString()
                Log.d("SecondAskActivity", "Place : $place")
            }
            startActivity(nextIntent)
        }
    }
}