package com.cpen321.usermanagement.data.remote.dto

data class EventDataRaw(
    val eventData: EventData
)

data class EventData(
    val name: String,
    val link: String,
    val location: String,
    val user: String
)