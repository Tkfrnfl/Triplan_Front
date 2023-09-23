package com.example.triplan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.example.triplan.R
import com.example.triplan.viewModel.GptViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//import org.koin.core.context.startKoin

@AndroidEntryPoint
class GptAskActivity : AppCompatActivity()  {
   private val gptViewModel:GptViewModel by viewModels()


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpt)

         val question: EditText =findViewById(R.id.gptEt1)


         val gptBtn: Button =findViewById(R.id.gptBtn2)
//         startKoin {
//             androidContext(this@GptAskActivity)
//             //modules(demoModule)
//             modules(demoViewModel)
//         }

         gptBtn.setOnClickListener{
//             CoroutineScope(Dispatchers.IO).launch{
//                 async { gptViewModel.questionAsk(question.text.toString()) }
//             }
             CoroutineScope(Dispatchers.Main).launch {
             val res =gptViewModel.firstjob(question.text.toString())
             val resList:ArrayList<String>
             resList= res.data!!.askGpt as ArrayList<String>

                 val nextIntent = Intent(this@GptAskActivity, GptResultActivity::class.java)
                 nextIntent.putExtra("res",resList);

                 startActivity(nextIntent)
             }
         }

    }
}