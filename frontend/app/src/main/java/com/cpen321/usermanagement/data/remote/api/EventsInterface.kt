package com.cpen321.usermanagement.data.remote.api

import com.cpen321.usermanagement.data.remote.dto.ApiResponse
import com.cpen321.usermanagement.data.remote.dto.AuthData
import com.cpen321.usermanagement.data.remote.dto.EventData
import com.cpen321.usermanagement.data.remote.dto.EventDataRaw
import com.cpen321.usermanagement.data.remote.dto.GoogleLoginRequest
import com.cpen321.usermanagement.data.remote.dto.HobbiesData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface EventsInterface {
    @GET("hobbyEvents")
    fun getAvailableEvents(@Header("Authorization") authHeader: String): Response<ApiResponse<EventDataRaw>>
}

