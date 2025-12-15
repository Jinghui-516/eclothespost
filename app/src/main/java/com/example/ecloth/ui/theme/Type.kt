package com.example.ecloth.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// -------------------------------------------------------------
// ✨ Typography：整個 App 的字型樣式設定
// Material3 預設提供多種文字類型（body, title, label ...）
// 這裡是從 bodyLarge 開始自訂
// -------------------------------------------------------------

val Typography = Typography(

    // ---------------------------------------------------------
    // 📝 bodyLarge：一般文章內容 / 主要敘述文字（預設使用）
    // ---------------------------------------------------------
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,   // 字體（預設字型）
        fontWeight = FontWeight.Normal,    // 字重 Normal（400）
        fontSize = 16.sp,                  // 文字大小 16sp
        lineHeight = 24.sp,                // 行高（比較好閱讀）
        letterSpacing = 0.5.sp             // 字距
    )

    /* ---------------------------------------------------------
       如果你想自訂更多文字樣式，可以解除下面的註解：
       ---------------------------------------------------------
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,                  // 大標題
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,                  // 小標籤（按鈕文字等）
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
