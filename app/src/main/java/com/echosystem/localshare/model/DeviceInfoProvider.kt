package com.echosystem.localshare.model

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.echosystem.localshare.network.NetworkInterfaceScanner
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceInfoProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val networkInterfaceScanner: NetworkInterfaceScanner
) {

    val deviceId: String by lazy {
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: Build.SERIAL
    }

    var deviceName: String
        get() {
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            return prefs.getString("device_name", Build.MODEL) ?: Build.MODEL
        }
        set(value) {
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit().putString("device_name", value).apply()
        }

    val localIp: String
        get() = networkInterfaceScanner.getIpAddress()
}
