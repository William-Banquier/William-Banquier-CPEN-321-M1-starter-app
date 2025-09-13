package com.cpen321.usermanagement.data.repository

import android.net.Uri
import com.cpen321.usermanagement.data.remote.dto.EventData
import com.cpen321.usermanagement.data.remote.dto.User

interface EventsRepository {
    fun getEvents(): Result<EventData>
}