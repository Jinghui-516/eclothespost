package com.example.ecloth

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.ecloth.ui.theme.CreatePostScreen
import com.example.ecloth.ui.theme.PostItem
import com.example.ecloth.ui.theme.Post


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val posts = remember {
        mutableStateListOf(
            Post(1, "Alice", R.drawable.karina, null, "今天的穿搭！"),
            Post(2, "Bob", R.drawable.weather, null, "今天天氣好好 🌞"),
            Post(3, "Cathy", R.drawable.coffee, null, "咖啡廳打卡 ☕")
        )
    }

    // 相機暫存圖片 Uri
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // 相機啟動器
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraImageUri != null) {
            // 拍照完成後 → 跳去發文頁，帶上相機拍的照片
            navController.navigate("create?imageUri=${Uri.encode(cameraImageUri.toString())}")
        }
    }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                navController = navController,
                onCameraClick = {
                    val uri = createImageUri(context)
                    cameraImageUri = uri
                    cameraLauncher.launch(uri)
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // 首頁
            composable("home") {
                LazyColumn(modifier = Modifier) {
                    items(posts) { post ->
                        PostItem(post)
                    }
                }
            }

            // 建立貼文（支援相機帶進來的照片）
            composable("create?imageUri={imageUri}") { backStackEntry ->
                val uriArg = backStackEntry.arguments?.getString("imageUri")
                val defaultUri = uriArg?.let { Uri.parse(it) }

                CreatePostScreen(
                    defaultImageUri = defaultUri,
                    onPostCreated = { imageUri, text ->
                        posts.add(Post(posts.size + 1, "You", null, imageUri, text))
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }



            // 個人頁面
            composable("profile") {
                ProfileScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = { Text("USER_NAME", color = Color(0xFF8B7A70)) },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF8B7A70))
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Chat", tint = Color(0xFF8B7A70))
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun BottomBar(navController: androidx.navigation.NavController, onCameraClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 10.dp), // 稍微縮進邊距讓圓角更明顯
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            color = Color(0xFF8B7A70), // ☕ 奶咖色
            shadowElevation = 10.dp,
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // 🔹 控制導覽列厚度（預設約80dp，可自行調整）
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
                }
                IconButton(onClick = { navController.navigate("create") }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                }
                IconButton(onClick = { onCameraClick() }) {
                    Icon(Icons.Filled.CameraAlt, contentDescription = "Camera", tint = Color.White)
                }
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.White)
                }
            }
        }
    }
}



// 建立 Uri 用來存相機照片
fun createImageUri(context: Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "photo_${System.currentTimeMillis()}.jpg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )!!
}
