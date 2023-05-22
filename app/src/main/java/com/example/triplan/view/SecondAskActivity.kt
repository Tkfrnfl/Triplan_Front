package com.example.triplan.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

@AndroidEntryPoint
class SecondAskActivity : AppCompatActivity() {

    private val tripPlaces = mutableListOf<EditText>()
    private var pageNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_2)

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

            sendRequest()
            val nextIntent = Intent(this, ConfirmActivity::class.java)

            startActivity(nextIntent)
        }
    }

    private fun sendRequest() {

        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://10.0.2.2:8080/graphql")
            .build()

        val intentFirstAskActivity = intent
        val startDate = intentFirstAskActivity.getStringExtra("start") ?: ""
        val endDate = intentFirstAskActivity.getStringExtra("end") ?: ""
        val location = intentFirstAskActivity.getStringExtra("location") ?: ""

        // PlanRequestDto를 위한 변수
        val planRequestDto = PlanRequestDto(startDate, endDate, location)

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

        runBlocking {
            try {

                val response: ApolloResponse<RequestPlanInformationMutation.Data> =
                    apolloClient.mutation(RequestPlanInformationMutation(planRequestDto, dayPlanRequestDtoList.toList())).execute()

                val data = response.data

                Log.v("Response", "$data")
            } catch (e: ApolloException) {
                throw ApolloException("통신 실패 에러")
            }
        }
    }
}