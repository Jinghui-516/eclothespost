package com.example.ecloth.ui.theme


import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api

data class Post(
    val id: Int,
    val userName: String,
    val imageRes: Int? = null, // Drawable 資源
    val imageUri: Uri? = null, // 相簿圖片 Uri
    val content: String,
    val likes: Int = 0
)





