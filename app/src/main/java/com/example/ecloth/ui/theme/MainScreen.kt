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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.ecloth.ui.theme.CreatePostScreen
import com.example.ecloth.ui.theme.LeaderboardScreen
import com.example.ecloth.ui.theme.PostItem
import com.example.ecloth.ui.theme.Post


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // 🔹 初始貼文列表（假資料）
    val posts = remember {
        mutableStateListOf(
            Post(1, "Alice", R.drawable.karina, null, "今天的穿搭！"),
            Post(2, "Bob", R.drawable.weather, null, "今天天氣好好 🌞"),
            Post(3, "Cathy", R.drawable.coffee, null, "咖啡廳打卡 ☕")
        )
    }
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }


    // 🔹 相機啟動器：TakePicture
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        // success = 相機拍照成功
        if (success && cameraImageUri != null) {
            // 拍照成功 → 跳到 CreatePost 頁面並帶著照片 Uri
            navController.navigate("create?imageUri=${Uri.encode(cameraImageUri.toString())}")
        }
    }
    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                navController = navController,// ✅ 將觸發相機的邏輯作為 onCameraClick 參數傳入
                onCameraClick = {
                    val uri = createImageUri(context)
                    cameraImageUri = uri
                    cameraLauncher.launch(uri)
                }
            )
        }
    ) { innerPadding ->
        // ... NavHost 內容不變 ...



        // 🔹 Navigation Graph 設定（首頁 / 發文 / 個人頁）
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // ---------------------------------------------
            // 📌 首頁頁面：顯示貼文列表
            // ---------------------------------------------
            composable("home") {
                LazyColumn {
                    items(posts) { post ->
                        PostItem(post)
                    }
                }
            }

            // ---------------------------------------------
            // 📌 建立貼文頁面（支援相機帶入照片）
            // ---------------------------------------------
            composable("create?imageUri={imageUri}") { backStackEntry ->
                // 取得從 Nav 傳來的照片 Uri
                val uriArg = backStackEntry.arguments?.getString("imageUri")
                val defaultUri = uriArg?.let { Uri.parse(it) }

                CreatePostScreen(
                    defaultImageUri = defaultUri,  // 相機照片或 null
                    onPostCreated = { imageUri, text ->
                        // 使用者按下「發佈」 → 新增貼文到列表
                        posts.add(
                            Post(
                                posts.size + 1,
                                "You",
                                null,
                                imageUri,
                                text
                            )
                        )
                        // 發佈完成 → 返回上一頁
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            // ---------------------------------------------
            // 📌 個人頁面
            // ---------------------------------------------
            composable("profile") {
                ProfileScreen()
            }
            // 📌 排行榜頁面
            composable("leaderboard") {
                LeaderboardScreen()
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
                Icon(
                    Icons.Outlined.ChatBubbleOutline,
                    contentDescription = "Chat",
                    tint = Color(0xFF8B7A70)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun BottomBar(navController: NavController, onCameraClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(bottom = 30.dp), // 🔹 整個底部導覽列往上抬高
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            color = Color(0xFF8B7A70),
            shadowElevation = 10.dp,
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)  // 🔹 想要導覽列更「細」就改這裡
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
                // ✅ 加回相機按鈕，並使用傳入的 onCameraClick

                IconButton(onClick = { navController.navigate("leaderboard") }) {
                    Icon(
                        imageVector = Icons.Default.Leaderboard, contentDescription = "Leaderboard", tint = Color.White
                    )
                }
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.White)
                }
            }
        }
    }
}
// ------------------------------------------------------------
// 📌 建立一個圖片 Uri，讓相機存照片到媒體庫
// ------------------------------------------------------------
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
