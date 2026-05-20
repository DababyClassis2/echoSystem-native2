package com.echosystem.localshare.model

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.Inet4Address
import java.net.NetworkInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceInfoProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @SuppressLint("HardwareIds")
    val deviceId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "unknown"

    var deviceName: String
        get() = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getString("device_name", Build.MODEL) ?: Build.MODEL
        set(value) {
            context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit().putString("device_name", value).apply()
        }

    val localIp: String
        get() = getIpAddress()

    internal fun getIpAddress(): String {
        val interfaces = NetworkInterface.getNetworkInterfaces().toList()
        for (networkInterface in interfaces) {
            for (inetAddress in networkInterface.inetAddresses.toList()) {
                if (inetAddress is Inet4Address && !inetAddress.isLoopbackAddress) {
                    return inetAddress.hostAddress ?: ""
                }
            }
        }
        return ""
    }
}
