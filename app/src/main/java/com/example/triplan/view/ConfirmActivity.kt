package com.example.triplan.view

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.R
import com.example.triplan.geocoding.GeocodingService
import com.example.triplan.view.result.DayPlan
import com.example.triplan.view.result.Plan
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ConfirmActivity: AppCompatActivity() {

    private val tripPlaceList: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        val intentSecondAskActivity=intent
        val touristArea:TextView=findViewById(R.id.touristAreaView)
        val linearLayout: LinearLayout = findViewById(R.id.tripConfirm)

        val resultString: String? = intentSecondAskActivity.getStringExtra("res")

        val jsonResponse = resultString.toString() // response.data.toString()의 값
        Log.d("resultString", jsonResponse)
        val startIndex = jsonResponse.indexOf("{", jsonResponse.indexOf("requestPlanInformation"))
        val endIndex = jsonResponse.lastIndexOf("}") + 1
        val planJson = jsonResponse.substring(startIndex, endIndex)
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ ->
                LocalDate.parse(json.asJsonPrimitive.asString)
            }).create()

        val plan = gson.fromJson(planJson, Plan::class.java)
        touristArea.text = plan.touristArea
        var tourdays = 1
        for (dayPlan in plan.dayPlans) {

            val textView = TextView(this@ConfirmActivity)
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val stringBuilder = StringBuilder()
            stringBuilder.append("${tourdays}일차\n")
            linearLayout.addView(textView)
            var idx = 1
            stringBuilder.append("${idx}. ").append(dayPlan.tripPlaces[0].location).append("\n")
            for (i: Int in 1 until dayPlan.tripPlaces.size - 1 step(3)) {
                idx += 1
                val placeName = dayPlan.tripPlaces[i].location.split("=")[1]
                stringBuilder.append("${idx}. ").append(placeName).append("\n")
            }
            stringBuilder.append("${idx + 1}. ").append(dayPlan.tripPlaces[dayPlan.tripPlaces.size - 1].location).append("\n\n")
            textView.text = stringBuilder.toString()
            tourdays += 1
        }

        Log.d("touristArea", touristArea.text.toString())
    }
}