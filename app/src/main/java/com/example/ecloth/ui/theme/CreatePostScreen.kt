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
    defaultImageUri: Uri?,
    onPostCreated: (Uri?, String) -> Unit,
    onBack: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // 選擇相簿圖片
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) selectedImageUri = uri
    }

    // 用來暫存拍照的 Uri
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // 相機拍照
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
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

        // 照片框
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
                .clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text("點擊選擇照片")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 拍照按鈕
        // 在 CreatePostScreen.kt 中

// ... (其他程式碼)

        // 拍照按鈕
        Button(
            onClick = {
                val photoFile = File(context.cacheDir, "temp_photo.jpg")
                // 1. 將 Uri存到一個本地變數
                val uriToLaunch = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider", // 要跟 AndroidManifest.xml 一致
                    photoFile
                )
                // 2. 更新狀態
                tempPhotoUri = uriToLaunch
                // 3. 使用本地變數來啟動 cameraLauncher
                cameraLauncher.launch(uriToLaunch)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("拍照")
        }
// ... (其他程式碼)


        Spacer(modifier = Modifier.height(16.dp))

        // 文字輸入
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("寫下你的貼文內容...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 發佈按鈕
        Button(
            onClick = { onPostCreated(selectedImageUri, text) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("發佈")
        }
    }
}
