package com.echosystem.localshare.discovery

import com.echosystem.localshare.model.DeviceCandidate
import com.echosystem.localshare.model.DeviceInfoProvider
import com.echosystem.localshare.network.NetworkInterfaceScanner
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import javax.inject.Inject

class UdpBroadcastDiscovery @Inject constructor(
    private val deviceInfoProvider: DeviceInfoProvider,
    private val networkInterfaceScanner: NetworkInterfaceScanner
) {
    private val BEACON_PORT = 45678

    fun discover(): Flow<DeviceCandidate> = channelFlow {
        val scope = CoroutineScope(Dispatchers.IO + Job())

        // Receiver
        scope.launch {
            val socket = DatagramSocket(BEACON_PORT, InetAddress.getByName("0.0.0.0"))
            socket.broadcast = true
            val buffer = ByteArray(1024)
            while (isActive) {
                val packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)
                val json = String(packet.data, 0, packet.length)
                // Parse JSON and emit
                // If beacon.deviceId != deviceInfoProvider.deviceId -> trySend(DeviceCandidate(...))
            }
        }

        // Sender
        scope.launch {
            while (isActive) {
                val interfaces = networkInterfaceScanner.getPhysicalInterfaces()
                interfaces.forEach { /* Send broadcast */ }
                delay(2000)
            }
        }

        awaitClose { scope.cancel() }
    }
}
