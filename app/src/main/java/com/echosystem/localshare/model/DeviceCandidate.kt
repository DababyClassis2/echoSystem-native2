package com.echosystem.localshare.model
import kotlinx.serialization.Serializable

@Serializable
data class DeviceCandidate(
    val ip: String,
    val port: Int,
    val source: String,   // "mdns" | "udp_broadcast" | "arp_cache" | "wifi_direct" | "ble"
)