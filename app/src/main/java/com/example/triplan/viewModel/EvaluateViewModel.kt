package com.example.triplan.viewModel

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.example.triplan.EvaluatePlanGetQuery
import com.example.triplan.EvaluatePlanPostMutation
import com.example.triplan.type.EvaluateRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EvaluateViewModel @Inject constructor():ViewModel(){

    suspend fun evaluateMutate(question:String){
        val apolloClient= ApolloClient.Builder()
            .serverUrl("http://192.168.219.105:8080/graphql")
            .build()

        apolloClient.mutation(EvaluatePlanPostMutation()).execute()
    }
    suspend fun evaluateQuery():
            ApolloResponse<EvaluatePlanGetQuery.Data> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl("http://192.168.219.105:8080/graphql")
            .build()

        val evaluateRequestDto: EvaluateRequestDto =
            EvaluateRequestDto(Optional.present(1))

        return apolloClient.query(EvaluatePlanGetQuery(Optional.present(evaluateRequestDto)))
            .execute()
    }

//    fun evaluateGet():{
//        val job= CoroutineScope(Dispatchers.IO)
//        job.launch {
//            val response:ApolloResponse<EvaluatePlanGetQuery.Data> = evaluateQuery();
//            return@launch response
//        }
//    }

}