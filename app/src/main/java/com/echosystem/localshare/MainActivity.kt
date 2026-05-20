package com.echosystem.localshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.echosystem.localshare.ui.theme.LocalShareTheme
import com.echosystem.localshare.ui.navigation.LocalShareNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalShareTheme {
                LocalShareNavHost()
            }
        }
    }
}