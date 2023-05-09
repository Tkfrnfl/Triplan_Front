package com.example.triplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import com.example.triplan.R
import com.example.triplan.demoViewModel
import com.example.triplan.viewModel.GptViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class GptAskActivity : AppCompatActivity()  {
    private  val gptViewModel:GptViewModel by viewModel()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpt)

         val question: EditText =findViewById(R.id.gptEt1)


         val gptBtn: Button =findViewById(R.id.gptBtn2)
         startKoin {
             androidContext(this@GptAskActivity)
             //modules(demoModule)
             modules(demoViewModel)
         }

         gptBtn.setOnClickListener{
//             CoroutineScope(Dispatchers.IO).launch{
//                 async { gptViewModel.questionAsk(question.text.toString()) }
//             }
             gptViewModel.questionAsk(question.text.toString())
         }

    }
}