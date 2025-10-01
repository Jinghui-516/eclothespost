package com.example.ecloth.ui.theme


import android.net.Uri

data class Post(
    val id: Int,
    val userName: String,
    val imageRes: Int? = null, // Drawable 資源
    val imageUri: Uri? = null, // 相簿圖片 Uri
    val content: String
)
