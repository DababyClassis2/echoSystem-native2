package com.echosystem.localshare.repository

import com.echosystem.localshare.model.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRegistry @Inject constructor() {
    private val _devices = MutableStateFlow<Map<String, Device>>(emptyMap())
    val devices = _devices.asStateFlow()

    fun register(device: Device) {
        _devices.value = _devices.value + (device.id to device)
    }

    fun getAll(): List<Device> = _devices.value.values.toList()

    fun remove(id: String) {
        _devices.value = _devices.value - id
    }
}
