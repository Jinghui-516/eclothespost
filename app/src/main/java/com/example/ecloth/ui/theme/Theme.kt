package com.example.ecloth.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// -------------------------------------------------------------
// 🌙 深色模式用的顏色組 Dark Theme Color Scheme
// -------------------------------------------------------------
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,        // 主要顏色（按鈕、強調色）
    secondary = PurpleGrey80,  // 次要顏色
    tertiary = Pink80          // 第三顏色（特殊用途）
)

// -------------------------------------------------------------
// ☀️ 淺色模式用的顏色組 Light Theme Color Scheme
// -------------------------------------------------------------
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    // 其他顏色預設使用 Material3 標準值，
    // 若要完整客製化可以在這裡解開註解調整：
    /*
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


// -------------------------------------------------------------
// 🎨 App 主主題：包住整個畫面的 Theme
// 所有 Composable 都會讀取此處的配色、字型、形狀設定
// -------------------------------------------------------------
@Composable
fun EclothTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),  // 若沒指定，使用系統深色模式判斷
    dynamicColor: Boolean = true,               // Android 12+ 可用動態顏色（Material You）
    content: @Composable () -> Unit
) {
    // 根據狀態決定使用哪一種配色方案
    val colorScheme = when {

        // ---------------------------------------------------------
        // 🌈 Android 12 以上 → 啟用 Material You 動態顏色
        // ---------------------------------------------------------
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            // 深色 / 淺色 對應不同動態配色
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        // ---------------------------------------------------------
        // 🌙 如果沒有動態顏色 → 使用自訂深色主題
        // ---------------------------------------------------------
        darkTheme -> DarkColorScheme

        // ---------------------------------------------------------
        // ☀️ 其餘情況 → 使用自訂淺色主題
        // ---------------------------------------------------------
        else -> LightColorScheme
    }

    // ---------------------------------------------------------
    // 將配色與字型注入 MaterialTheme → 包住所有 UI
    // ---------------------------------------------------------
    MaterialTheme(
        colorScheme = colorScheme,  // 指定本 App 的顏色
        typography = Typography,    // 字型（在 Typography.kt 定義）
        content = content           // 內容畫面
    )
}
