package com.example.triplan.view.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import com.example.triplan.R
import com.example.triplan.view.MainActivity
import com.example.triplan.viewModel.EvaluateViewModel
import com.example.triplan.viewModel.GptViewModel
import com.google.android.material.internal.ViewUtils.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyScrapActivity: AppCompatActivity() {
    private val evaluateViewModel: EvaluateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myscrap)

        val planBtn: Button = findViewById(R.id.planBtn)
        val scrapBtn: Button = findViewById(R.id.scrapBtn)
        val homeBtn: Button = findViewById(R.id.homeBtn)


        CoroutineScope(Dispatchers.Main).launch {
            val response=evaluateViewModel.evaluateQuery()
            if(response!=null){
                println(response.data!!.requestEvaluateInfo!!.size)
                for(i in 0 until response.data!!.requestEvaluateInfo!!.size ){
                    val listView:ConstraintLayout=findViewById(R.id.myscrap)
                    var tvPlan = TextView(applicationContext)
                    val tvDetail = TextView(applicationContext)
                    val tvRating = TextView(applicationContext)
                    val params: LinearLayout.LayoutParams =
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)

                    tvPlan.text=response.data!!.requestEvaluateInfo!![i]!!.planId.toString()
                    tvDetail.text=response.data!!.requestEvaluateInfo!![i]!!.detail.toString()
                    tvRating.text=response.data!!.requestEvaluateInfo!![i]!!.rating.toString()

                    tvPlan.y=(200*(i+1)).toFloat()
                    tvDetail.y=(200*(i+1)).toFloat()
                    tvRating.y=(200*(i+1)).toFloat()
                    tvPlan.x=50f
                    tvRating.x=250f
                    tvDetail.x=550f

                    tvPlan.layoutParams=params
                    tvDetail.layoutParams=params
                    tvRating.layoutParams=params
                    listView.addView(tvPlan)
                    listView.addView(tvDetail)
                    listView.addView(tvRating)
                }
            }

        }

        planBtn.setOnClickListener {
            val nextIntent = Intent(this, MyPlanActivity::class.java)
            startActivity(nextIntent)
        }

        scrapBtn.setOnClickListener {
            val nextIntent = Intent(this, MyScrapActivity::class.java)
            startActivity(nextIntent)
        }

        homeBtn.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }

    }
}