package com.example.triplan.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.apolloExceptionHandler
import com.example.triplan.AskGptMutation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class GptViewModel @Inject constructor() :ViewModel(){

    suspend fun firstjob(question:String){
        Log.d("일단인풋4","")
        val apolloClient=ApolloClient.Builder()
            .serverUrl("http://192.168.219.105:8080/graphql")
            .build()

        apolloClient.mutation(AskGptMutation(question)).execute()
    }

    fun questionAsk(question:String){
        Log.d("일단인풋1","")
        val job= CoroutineScope(Dispatchers.IO)
        job.launch {
            firstjob(question)
        }

    }
}




