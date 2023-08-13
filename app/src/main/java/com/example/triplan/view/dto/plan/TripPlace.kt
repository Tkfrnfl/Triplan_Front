package com.example.triplan.view.dto.plan

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDateTime

@Serializable
class TripPlace {
    var id: Long = 0L

    var name: String? = null

    var location: String? = null

    @Transient
    var transitTime: LocalDateTime? = null

    var transports: String? = null

    @Transient
    var leadTime: LocalDateTime? = null

    var toll: Int = 0
}