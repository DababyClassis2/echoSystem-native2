package com.echosystem.localshare.discovery

import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.ParcelUuid
import com.echosystem.localshare.model.DeviceCandidate
import com.echosystem.localshare.model.DeviceInfoProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class BleDiscovery @Inject constructor(
    @ApplicationContext private val context: Context,
    private val deviceInfoProvider: DeviceInfoProvider
) {
    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val adapter = bluetoothManager.adapter
    private val advertiser = adapter?.bluetoothLeAdvertiser
    private val scanner = adapter?.bluetoothLeScanner
    private val MANUFACTURER_ID = 0x4C53

    fun discover(): Flow<DeviceCandidate> = channelFlow {
        if (adapter == null || !adapter.isEnabled) return@channelFlow

        // Advertise
        val settings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER)
            .setConnectable(false)
            .build()
        
        // Scan
        val filter = ScanFilter.Builder()
            .setManufacturerData(MANUFACTURER_ID, byteArrayOf())
            .build()
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        val callback = object : android.bluetooth.le.ScanCallback() {
            override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult) {
                // Parse manufacturer data and emit
                // trySend(DeviceCandidate(result.device.address, parsedPort, "ble"))
            }
        }

        scanner?.startScan(listOf(filter), scanSettings, callback)

        awaitClose { scanner?.stopScan(callback) }
    }
}
