package com.example.triplan.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import com.example.triplan.LoginMutation
import com.example.triplan.R
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.IOException

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val kakaoLoginBtn: Button = findViewById(R.id.kakaoLoginButton)

        kakaoLoginBtn.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if (token != null) {
                    saveAccessToken(token.accessToken)
                    sendRequestWithAccessToken()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else if (error != null) {
                    Log.e("Error", "$error")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun sendRequestWithAccessToken() {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method, original.body)
                builder.header("Authorization", "Bearer ${getAccessToken()}") // 여기에 토큰을 넣으세요.
                chain.proceed(builder.build())
            }
            .build()

        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://10.0.2.2:8080/graphql")
            .okHttpClient(okHttpClient)
            .build()

        runBlocking {
            try {
                val response: ApolloResponse<LoginMutation.Data> = apolloClient.mutation(LoginMutation()).execute()
                val data = response.data.toString()

                Log.v("Response", "$data")
            } catch (e: ApolloException) {
                throw ApolloException("통신 실패 에러")
            }
        }
    }

    private fun getAccessToken(): String? {
        val sharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }

    private fun saveAccessToken(accessToken: String) {
        val sharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", accessToken)
        editor.apply()
    }
}