package com.echosystem.localshare.discovery

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo
import com.echosystem.localshare.model.DeviceCandidate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class WifiDirectDiscovery @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val manager = context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
    private val p2pChannel = manager.initialize(context, context.mainLooper, null)

    fun discover(): Flow<DeviceCandidate> = callbackFlow {
        val serviceInfo = WifiP2pDnsSdServiceInfo.newInstance("LocalShare", "_localshare._tcp.", mapOf("port" to "8080"))
        
        manager.addLocalService(p2pChannel, serviceInfo, null)
        
        val serviceResponseListener = WifiP2pManager.DnsSdServiceResponseListener { _, _, srcDevice ->
            trySend(DeviceCandidate(srcDevice.deviceAddress, 8080, "wifi_direct"))
        }
        
        manager.setDnsSdResponseListeners(p2pChannel, serviceResponseListener, null)
        manager.discoverServices(p2pChannel, null)
        
        awaitClose {
            manager.removeGroup(p2pChannel, null)
            manager.clearLocalServices(p2pChannel, null)
            manager.clearServiceRequests(p2pChannel, null)
        }
    }
}
