package com.echosystem.localshare.model
import kotlinx.serialization.Serializable

@Serializable
enum class TransferDirection { UPLOAD, DOWNLOAD }

@Serializable
enum class TransferState { QUEUED, ACTIVE, PAUSED, COMPLETE, FAILED, CANCELLED }

@Serializable
data class TransferSession(
    val id: String,
    val fileName: String,
    val totalBytes: Long,
    val transferredBytes: Long = 0L,
    val speedBps: Long = 0L,
    val direction: TransferDirection,
    val state: TransferState,
    val targetDeviceId: String,
    val startedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val error: String? = null,
) {
    val progressPercent: Float get() =
        if (totalBytes > 0) transferredBytes.toFloat() / totalBytes else 0f

    val speedLabel: String get() = when {
        speedBps > 1_000_000 -> "%.1f MB/s".format(speedBps / 1_000_000f)
        speedBps > 1_000     -> "%.1f KB/s".format(speedBps / 1_000f)
        else                 -> "${speedBps} B/s"
    }
}
