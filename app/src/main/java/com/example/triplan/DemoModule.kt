package com.example.triplan

import com.example.triplan.viewModel.GptViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val demoViewModel= module {
    viewModel{
        GptViewModel()
    }
}