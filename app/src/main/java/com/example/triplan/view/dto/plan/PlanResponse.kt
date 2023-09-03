package com.example.triplan.view.dto.plan

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDate

@Serializable
class PlanResponse{
    var id:Long = 0L

    @Transient
    var startDate: LocalDate? = null

    @Transient
    var endDate: LocalDate? = null

    var touristArea: String? = null

    var feedBack: Int = 0

    var feedBackNumber: Int = 0

    var dayPlans: MutableList<DayPlan> = mutableListOf()
}
