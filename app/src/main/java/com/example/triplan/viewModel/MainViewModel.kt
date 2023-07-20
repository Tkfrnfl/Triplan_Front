package com.example.triplan.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.ApolloClient
import com.example.triplan.AskGptMutation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor():ViewModel(){

    suspend fun firstjob(question:String){
        val apolloClient= ApolloClient.Builder()
            .serverUrl("http://192.168.219.105:8080/graphql")
            .build()

        apolloClient.mutation(AskGptMutation(question)).execute()
    }


}