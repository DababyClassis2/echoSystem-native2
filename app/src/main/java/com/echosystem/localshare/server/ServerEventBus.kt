package com.echosystem.localshare.server

import com.echosystem.localshare.model.ServerEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerEventBus @Inject constructor() {
    private val _eventFlow = MutableSharedFlow<ServerEvent>(replay = 0, extraBufferCapacity = 64)
    val eventFlow = _eventFlow.asSharedFlow()

    suspend fun emit(event: ServerEvent) {
        _eventFlow.emit(event)
    }
}
