package com.example.triplan.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.apolloExceptionHandler
import com.example.triplan.AskGptMutation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class GptViewModel @Inject constructor() :ViewModel(){

    suspend fun firstjob(question:String):ApolloResponse<AskGptMutation.Data>{
        Log.d("인풋","")
        val apolloClient=ApolloClient.Builder()
            .serverUrl("http://211.105.47.82:8080/graphql")
            .build()

       return apolloClient.mutation(AskGptMutation(question)).execute()
    }

//    fun questionAsk(question:String):ApolloResponse<AskGptMutation.Data>{
//        Log.d("일단인풋1","")
//        val job= CoroutineScope(Dispatchers.IO)
//        job.launch {
//           val res= firstjob(question)
//            Log.d("gpt",res.toString())
//            return res;
//        }
//
//    }
}




