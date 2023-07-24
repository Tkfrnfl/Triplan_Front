package com.example.triplan.viewModel

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.ApolloClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(): ViewModel() {

    suspend fun withdrawal() {
        val apolloClient= ApolloClient.Builder()
            .serverUrl("http://10.0.0.2:8080/graphql")
            .build()

    }
}