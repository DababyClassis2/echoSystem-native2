package com.echosystem.localshare.model
import kotlinx.serialization.Serializable

@Serializable
data class SharedFile(
    val id: String,
    val name: String,
    val size: Long,
    val mimeType: String,
    val absolutePath: String,
    val fromDeviceId: String,
    val createdAt: Long = System.currentTimeMillis(),
)
