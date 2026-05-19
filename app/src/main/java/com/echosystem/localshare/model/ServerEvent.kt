package com.echosystem.localshare.model
import kotlinx.serialization.Serializable

@Serializable
sealed class ServerEvent {
    abstract val type: String

    @Serializable
    data class FileReceived(val file: SharedFile) : ServerEvent() {
        override val type = "FILE_RECEIVED"
    }
    @Serializable
    data class FileDeleted(val fileId: String) : ServerEvent() {
        override val type = "FILE_DELETED"
    }
    @Serializable
    data class DeviceJoined(val device: Device) : ServerEvent() {
        override val type = "DEVICE_JOINED"
    }
    @Serializable
    data class DeviceLeft(val deviceId: String) : ServerEvent() {
        override val type = "DEVICE_LEFT"
    }
    @Serializable
    data class TransferProgress(val session: TransferSession) : ServerEvent() {
        override val type = "TRANSFER_PROGRESS"
    }
}
