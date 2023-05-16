package com.example.triplan.view

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.triplan.view.dto.user.UserInfoDto
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

@AndroidEntryPoint
class MyInfoActivity: AppCompatActivity() {

    private val accessToken = getSharedPreferences("", Context.MODE_PRIVATE).getString("accessToken", null)
    private val client = OkHttpClient()

    private fun sendRequesteWithAccessToken() {


        val request = Request.Builder()
            .url("http://10.0.2.2:8080/graphql")
            .addHeader("Authorization", "Bearer $accessToken")
            .post("".toRequestBody(null))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 통신 실패 처리
                Log.e("Error", "$e")
                // TODO: 통신 실패 시 동작 추가
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    val gson = Gson()
                    val userInfoDto = gson.fromJson(responseData, UserInfoDto::class.java)
                    Log.v("userInfo", userInfoDto.email)
                    // 응답 처리
                    // TODO: 응답 데이터 처리
                } else {
                    // 응답 실패 처리
                    Log.e("Error on onResponse", "${response.code}")
                    // TODO: 응답 실패 시 동작 추가
                }
            }
        })
    }
}