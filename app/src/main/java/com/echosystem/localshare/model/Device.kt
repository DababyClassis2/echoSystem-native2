package com.echosystem.localshare.model
import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val id: String,
    val name: String,
    val ip: String,
    val port: Int,
    val os: String,        // "android" | "ios" | "windows" | "unknown"
    val version: String,
    val isPaired: Boolean = false,
    val lastSeen: Long = System.currentTimeMillis(),
    val latencyMs: Int? = null,
)
