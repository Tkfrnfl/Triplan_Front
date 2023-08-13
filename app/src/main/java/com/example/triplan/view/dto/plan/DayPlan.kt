package com.example.triplan.view.dto.plan

import kotlinx.serialization.Serializable

@Serializable
class DayPlan {
    var id: Long = 0L

    var startingPoint: String? = null

    var destination: String? = null

    var tripPlaces: MutableList<TripPlace> = mutableListOf()
}