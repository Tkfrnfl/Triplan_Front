package com.example.triplan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.triplan.R
import com.example.triplan.viewModel.GptViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GptAskActivity : AppCompatActivity()  {
    private  val gptViewModel:GptViewModel by viewModel()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gptActivity_main)

         val question: EditText =findViewById(R.id.gptEt1)
    }
}