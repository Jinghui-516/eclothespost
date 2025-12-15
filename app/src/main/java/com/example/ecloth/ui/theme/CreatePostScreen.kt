package com.example.ecloth.ui.theme

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun CreatePostScreen(
    defaultImageUri: Uri?,                     // 預設圖片（如果從外面傳入）
    onPostCreated: (Uri?, String) -> Unit,     // 建立完成後要回傳：圖片 + 文字內容
    onBack: () -> Unit                         // 返回按鈕（目前未使用）
) {
    var text by remember { mutableStateOf("") }           // 使用者輸入的貼文文字
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) } // 使用者選擇的圖片 Uri

    val context = LocalContext.current

    // -----------------------------
    // 1. 相簿選圖
    // -----------------------------
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        // 使用者從相簿選到圖片後更新
        if (uri != null) selectedImageUri = uri
    }

    // -----------------------------
    // 2. 暫存相機拍照所得的 Uri
    // -----------------------------
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // -----------------------------
    // 3. 相機拍照
    // -----------------------------
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        // 拍照成功後，把暫存的 Uri 變成選擇的圖片
        if (success) {
            selectedImageUri = tempPhotoUri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("建立貼文", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // 4. 顯示圖片 + 點擊開啟相簿
        // -----------------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
                .clickable { galleryLauncher.launch("image/*") }, // 點擊選擇相簿圖片
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                // 已選圖片 → 顯示
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // 未選圖片 → 顯示提示文字
                Text("點擊選擇照片")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // 5. 拍照按鈕
        // -----------------------------
        Button(
            onClick = {
                // 在 cache 目錄建立暫時照片檔案
                val photoFile = File(context.cacheDir, "temp_photo.jpg")

                // 用 FileProvider 轉成 content:// 的 Uri（一定要跟 Manifest provider 一樣）
                val uriToLaunch = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    photoFile
                )

                // 記住這次拍照要存到的 Uri
                tempPhotoUri = uriToLaunch

                // 啟動相機
                cameraLauncher.launch(uriToLaunch)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("拍照")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // 6. 貼文文字輸入框
        // -----------------------------
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("寫下你的貼文內容...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -----------------------------
        // 7. 發佈按鈕（回傳圖片 Uri + 文字）
        // -----------------------------
        Button(
            onClick = { onPostCreated(selectedImageUri, text) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("發佈")

        }
    }
}
