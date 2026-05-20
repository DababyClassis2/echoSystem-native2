package com.echosystem.localshare.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.echosystem.localshare.model.DeviceInfoProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    deviceInfoProvider: DeviceInfoProvider
) : ViewModel() {
    private val _localIp = MutableStateFlow(deviceInfoProvider.localIp)
    val localIp = _localIp.asStateFlow()
    
    // Additional state for server, counts, etc.
}
