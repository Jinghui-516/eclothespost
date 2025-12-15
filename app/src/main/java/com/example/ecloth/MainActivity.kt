package com.example.ecloth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.ecloth.ui.theme.EclothTheme

// 使用 ExperimentalMaterial3Api 的標註（因為 MainScreen 可能使用了實驗性 API）
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    // App 的入口點，Activity 建立時會呼叫 onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 啟用 Edge-to-Edge UI，讓畫面可以延伸到狀態列與導航列底下
        enableEdgeToEdge()

        // setContent 用來放置 Jetpack Compose 的 UI
        setContent {

            // 套用自訂的 Compose Theme（顏色 / 排版 / 形狀等）
            EclothTheme {

                // 呼叫你的主要畫面 Composable
                MainScreen()
            }
        }
    }
}
