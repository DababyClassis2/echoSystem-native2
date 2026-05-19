package com.echosystem.localshare.model
import kotlinx.serialization.Serializable

@Serializable
data class DeviceInfoResponse(
    val id: String,
    val name: String,
    val os: String,
    val version: String,
    val port: Int,
) {
    fun toDevice(ip: String, port: Int) = Device(
        id = id, name = name, ip = ip, os = os,
        version = version, port = port,
    )
}
