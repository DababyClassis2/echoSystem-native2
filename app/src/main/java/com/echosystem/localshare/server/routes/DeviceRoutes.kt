package com.echosystem.localshare.server.routes

import com.echosystem.localshare.model.DeviceInfoResponse
import com.echosystem.localshare.model.DeviceInfoProvider
import com.echosystem.localshare.repository.DeviceRegistry
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRoutes @Inject constructor(
    private val deviceRegistry: DeviceRegistry,
    private val deviceInfoProvider: DeviceInfoProvider
) {
    fun register(route: Route) {
        route.route("/devices") {
            get {
                call.respond(deviceRegistry.getAll())
            }
            post("/register") {
                val device = call.receive<com.echosystem.localshare.model.Device>()
                deviceRegistry.register(device)
                call.respond(io.ktor.http.HttpStatusCode.OK)
            }
        }
        route.get("/info") {
            call.respond(DeviceInfoResponse(
                deviceInfoProvider.deviceId,
                deviceInfoProvider.deviceName,
                "android",
                "1.0",
                8080
            ))
        }
    }
}
