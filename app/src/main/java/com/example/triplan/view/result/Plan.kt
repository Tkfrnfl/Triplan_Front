package com.example.triplan.view.result

import java.time.LocalDate

data class Plan(val startDate: LocalDate, val endDate: LocalDate, val touristArea: String,
                val dayPlans: MutableList<DayPlan>)