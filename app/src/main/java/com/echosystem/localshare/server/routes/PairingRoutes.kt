package com.echosystem.localshare.server.routes

import com.echosystem.localshare.security.PairingManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PairingRoutes @Inject constructor(
    private val pairingManager: PairingManager
) {
    fun register(route: Route) {
        route.route("/pairing") {
            get("/status") {
                call.respond(mapOf("requiresPin" to pairingManager.requiresPin(), "isPaired" to false))
            }
            post("/authenticate") {
                val body = call.receive<Map<String, String>>()
                val pin = body["pin"]
                if (pin != null && pairingManager.verifyPin(pin)) {
                    call.respond(mapOf("token" to UUID.randomUUID().toString()))
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}
