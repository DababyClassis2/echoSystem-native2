package com.echosystem.localshare.server.routes

import com.echosystem.localshare.server.ServerEventBus
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketRoutes @Inject constructor(
    private val serverEventBus: ServerEventBus
) {
    private val connections = CopyOnWriteArrayList<DefaultWebSocketSession>()

    fun register(route: Route) {
        route.webSocket("/ws") {
            connections.add(this)
            try {
                serverEventBus.eventFlow.collect { event ->
                    send(Json.encodeToString(event))
                }
            } finally {
                connections.remove(this)
            }
        }
    }
}
