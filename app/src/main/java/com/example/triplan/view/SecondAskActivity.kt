package com.example.triplan.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import com.example.triplan.R
import com.example.triplan.RequestPlanInformationMutation
import com.example.triplan.type.DayPlanRequestDto
import com.example.triplan.type.PlanRequestDto
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

@AndroidEntryPoint
class SecondAskActivity : AppCompatActivity() {

    private val tripPlaces = mutableListOf<AutoCompleteTextView>()
    private var pageNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_2)

        Places.initialize(applicationContext, "AIzaSyBRcZ_Jxt8n_t_qktBk5MwGniDVoZiTD_Y")

        val intentByAskActivity = intent;

        this.pageNumber = intentByAskActivity.getIntExtra("days", 0)
        val addBtn: Button = findViewById(R.id.addButton)
        val linearLayout: LinearLayout = findViewById(R.id.tripPlace)

        val addButton: Button = findViewById(R.id.addButton) // 이곳에 실제 버튼의 id를 입력하세요
        val nextBtn: Button = findViewById(R.id.secondAskNextBtn) // 이곳에 실제 버튼의 id를 입력하세요
        val confirmBtn: Button = findViewById(R.id.planConfirmBtn)


        if (this.pageNumber > 0) {
            confirmBtn.visibility = View.GONE
        }

        if (this.pageNumber == 0){
            nextBtn.visibility = View.GONE
        }

        Log.d("pageNumber", this.pageNumber.toString())
        Log.d("intentTest", intentByAskActivity.getStringExtra("start").toString())

        val startDay = intentByAskActivity.getStringExtra("start")
        val endDay = intentByAskActivity.getStringExtra("end")
        val location = intentByAskActivity.getStringExtra("location")
        val days = intentByAskActivity.getIntExtra("days", 0)
        val noD = intentByAskActivity.getIntExtra("NoD", 0)

        addBtn.setOnClickListener {
            val newAutoCompleteTextView = AutoCompleteTextView(this)
            newAutoCompleteTextView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            newAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    performAutoComplete(s.toString(), newAutoCompleteTextView)
                }

                override fun afterTextChanged(s: Editable) {}
            })

            linearLayout.removeView(addBtn)

            linearLayout.addView(newAutoCompleteTextView)
            tripPlaces.add(newAutoCompleteTextView)

            linearLayout.addView(addBtn)
        }

        nextBtn.setOnClickListener {
            val nextIntent = Intent(this, SecondAskActivity::class.java)
            tripPlaces.forEach { places ->
                val place = places
                Log.d("SecondAskActivity", "Place : $place")
            }
            nextIntent.putExtra("start", startDay)
            nextIntent.putExtra("end", endDay)
            nextIntent.putExtra("location", location)
            nextIntent.putExtra("days", days - 1)
            nextIntent.putExtra("NoD", noD)

            for (i in pageNumber + 1..noD) {
                val tripPlacesText = intentByAskActivity.getStringArrayExtra("tripPlaces$i")
                nextIntent.putExtra("tripPlaces$i", tripPlacesText)
            }

            val tripPlacesText = tripPlaces.map { it.text.toString() }
            nextIntent.putExtra("tripPlaces$pageNumber", tripPlacesText.toTypedArray())

            startActivity(nextIntent)
        }

        confirmBtn.setOnClickListener {

            val res:String= sendRequest()
            val nextIntent = Intent(this, ConfirmActivity::class.java)
            nextIntent.putExtra("res",res)
            startActivity(nextIntent)
        }
    }

    private fun sendRequest() :String{

        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://192.168.219.105:8080/graphql")
            .build()

        val intentFirstAskActivity = intent
        val startDate = intentFirstAskActivity.getStringExtra("start") ?: ""
        val endDate = intentFirstAskActivity.getStringExtra("end") ?: ""
        val location = intentFirstAskActivity.getStringExtra("location") ?: ""

        // PlanRequestDto를 위한 변수
        //val planRequestDto = PlanRequestDto(startDate, endDate, location)
        val planRequestDto = PlanRequestDto("1996-03-15", "1996-03-15", location)
        //임시 변수 설정, Date type 화 필요

        // [DayPlanRequestDto]를 위한 변수
        val dayPlanRequestDtoList = mutableListOf<DayPlanRequestDto>()

        for (i in pageNumber + 1..intentFirstAskActivity.getIntExtra("NoD", 0)) {
            val trip = intentFirstAskActivity.getStringArrayExtra("tripPlaces$i")!!.toList()
            dayPlanRequestDtoList.add(DayPlanRequestDto(trip[0], trip, trip[trip.size - 1]))
        }
        val tripPlaceText = tripPlaces.map { it.text.toString() }
        dayPlanRequestDtoList.add(DayPlanRequestDto(tripPlaceText[0], tripPlaceText, tripPlaceText[tripPlaceText.size - 1]))

        for (i in 0 until dayPlanRequestDtoList.size) {
            Log.d("DayPlanRequestDto", "${dayPlanRequestDtoList[i]}")
        }
        var response: ApolloResponse<RequestPlanInformationMutation.Data>
        runBlocking {
            try {

                response =
                    apolloClient.mutation(RequestPlanInformationMutation(planRequestDto, dayPlanRequestDtoList.toList())).execute()
                Log.d("data chk",planRequestDto.toString())
                Log.d("data chk",dayPlanRequestDtoList.toList().toString())
                Log.d("res chk", response.data.toString())
                //val nextIntent = Intent(this@SecondAskActivity, ConfirmActivity::class.java)

                //nextIntent.putExtra("res",response.data.toString())


            } catch (e: ApolloException) {
                Log.d("res chk", e.message.toString())
                throw ApolloException("통신 실패 에러")
            }
        }
        return  response.data.toString()
    }

    private fun performAutoComplete(query: String, autoCompleteTextView: AutoCompleteTextView) {

        val placesClient: PlacesClient = Places.createClient(this)

        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            val predictionList = response.autocompletePredictions.map { it.getFullText(null).toString() }
            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, predictionList)
            autoCompleteTextView.setAdapter(adapter)
        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                Log.e(TAG, "Place not found: " + exception.statusCode)
            }
        }
    }
}