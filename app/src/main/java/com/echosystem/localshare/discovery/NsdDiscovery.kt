package com.echosystem.localshare.discovery

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import com.echosystem.localshare.model.DeviceCandidate
import com.echosystem.localshare.model.DeviceInfoProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import java.util.concurrent.LinkedBlockingQueue
import javax.inject.Inject

class NsdDiscovery @Inject constructor(
    @ApplicationContext private val context: Context,
    private val deviceInfoProvider: DeviceInfoProvider
) {
    private val nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
    private val resolveQueue = LinkedBlockingQueue<NsdServiceInfo>()
    private var isResolving = false

    fun discover(): Flow<DeviceCandidate> = channelFlow {
        val discoveryListener = object : NsdManager.DiscoveryListener {
            override fun onDiscoveryStarted(regType: String) {}
            override fun onDiscoveryStopped(regType: String) {}
            override fun onServiceFound(service: NsdServiceInfo) {
                if (service.serviceType == "_localshare._tcp.") {
                    resolveService(service) { candidate -> trySend(candidate) }
                }
            }
            override fun onServiceLost(service: NsdServiceInfo) {}
            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {}
            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {}
        }

        nsdManager.discoverServices("_localshare._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        
        awaitClose { nsdManager.stopServiceDiscovery(discoveryListener) }
    }

    private fun resolveService(service: NsdServiceInfo, onResolved: (DeviceCandidate) -> Unit) {
        nsdManager.resolveService(service, object : NsdManager.ResolveListener {
            override fun onServiceResolved(resolvedService: NsdServiceInfo) {
                onResolved(DeviceCandidate(resolvedService.host.hostAddress!!, resolvedService.port, "nsd"))
            }
            override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {}
        })
    }
}
