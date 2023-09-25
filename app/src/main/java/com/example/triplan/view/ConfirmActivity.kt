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

    private var geocoder: Geocoder? = null
    private val tripPlaceList: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        geocoder = Geocoder(this)
        setContentView(R.layout.activity_confirm)

        val intentSecondAskActivity=intent
        val touristArea:TextView=findViewById(R.id.touristAreaView)
        val linearLayout: LinearLayout = findViewById(R.id.tripConfirm)

        val resultString: String? = intentSecondAskActivity.getStringExtra("res")

        val jsonResponse = resultString.toString() // response.data.toString()의 값
        val startIndex = jsonResponse.indexOf("{", jsonResponse.indexOf("requestPlanInformation"))
        val endIndex = jsonResponse.lastIndexOf("}") + 1
        val planJson = jsonResponse.substring(startIndex, endIndex)
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ ->
                LocalDate.parse(json.asJsonPrimitive.asString)
            }).create()

        /*val plan = gson.fromJson(planJson, Plan::class.java)
        touristArea.text = plan.touristArea

        CoroutineScope(Dispatchers.Main).launch {
            for (dayPlan in plan.dayPlans) {

                val textView = TextView(this@ConfirmActivity)
                textView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val stringBuilder = StringBuilder()
                linearLayout.addView(textView)
                for (tripPlace in dayPlan.tripPlaces) {
                    if (tripPlace.location.isNullOrEmpty()) {
                        continue
                    }
                    val parts = tripPlace.location.split(", ")

                    val longitude = parts[0].trim().toDouble()
                    val latitude = parts[1].trim().toDouble()
                    var name: String = ""
                    withContext(Dispatchers.IO) {
                        changeLocationToAddress(latitude, longitude)
                    }
                }
                withContext(Dispatchers.Main) {
                    val length = tripPlaceList.size

                    for (i in 0 until length - 1) {
                        stringBuilder.append(tripPlaceList.get(i)).append(" -> ")
                    }

                    stringBuilder.append(tripPlaceList.get(length - 1))

                    textView.text = stringBuilder.toString()
                    Log.d("textView", textView.text.toString())
                    tripPlaceList.clear()
                }
            }
        }*/
    }
}