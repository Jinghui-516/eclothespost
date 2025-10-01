package com.example.ecloth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter

data class Post(
    val id: Int,
    val userName: String,
    val imageRes: Int? = null,
    val imageUri: Uri? = null,
    val content: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val posts = remember { mutableStateListOf(
        Post(1, "Alice", R.drawable.karina, null, "今天的穿搭！"),
        Post(2, "Bob", R.drawable.weather, null, "今天天氣好好 🌞"),
        Post(3, "Cathy", R.drawable.coffee, null, "咖啡廳打卡 ☕")
    )}

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(innerPadding)) {
            composable("home") {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(posts) { post ->
                        PostItem(post)
                    }
                }
            }
            composable("create") {
                CreatePostScreen(onPostCreated = { imageUri, text ->
                    posts.add(Post(posts.size + 1, "You", null, imageUri, text))
                    navController.popBackStack()
                }, onBack = { navController.popBackStack() })
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
fun BottomBar(navController: androidx.navigation.NavController) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.Red) },
            selected = true,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.Red) },
            selected = false,
            onClick = { navController.navigate("create") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.CameraAlt, contentDescription = "Camera", tint = Color.Red) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.Red) },
            selected = false,
            onClick = {}
        )
    }
}

@Composable
fun PostItem(post: Post) {
    var liked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(0) }
    var commentText by remember { mutableStateOf(TextFieldValue("")) }
    var comments by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {

        // 使用者頭像 + 名稱
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(36.dp).background(Color.Gray, CircleShape))
            Spacer(modifier = Modifier.width(8.dp))
            Text(post.userName)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // 貼文圖片
        if (post.imageRes != null) {
            Image(
                painter = painterResource(id = post.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
        } else if (post.imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(post.imageUri),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(post.content)

        Spacer(modifier = Modifier.height(8.dp))

        // 按鈕列
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Like",
                tint = if (liked) Color.Red else Color.Gray,
                modifier = Modifier.size(28.dp).clickable {
                    liked = !liked
                    likeCount += if (liked) 1 else -1
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("$likeCount 個讚")

            Spacer(modifier = Modifier.width(16.dp))

            Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Comment", tint = Color.Gray, modifier = Modifier.size(28.dp).clickable {
                expanded = !expanded
            })
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 留言輸入框
        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            placeholder = { Text("留言...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {
                if (commentText.text.isNotBlank()) {
                    comments = comments + commentText.text
                    commentText = TextFieldValue("")
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("送出")
        }

        // 留言列表
        if (comments.isNotEmpty()) {
            val displayedComments = if (expanded || comments.size <= 2) comments else comments.take(2)

            Column {
                displayedComments.forEach { comment ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(28.dp).background(Color.LightGray, CircleShape))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("${post.userName}：$comment")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                if (comments.size > 2) {
                    Text(
                        text = if (expanded) "收合留言" else "查看更多留言",
                        color = Color.Gray,
                        modifier = Modifier.padding(4.dp).clickable { expanded = !expanded }
                    )
                }
            }
        }

        // 分隔線
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun CreatePostScreen(onPostCreated: (Uri?, String) -> Unit, onBack: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("建立貼文", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.LightGray).clickable {
                launcher.launch("image/*")
            },
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
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("寫下你的貼文內容...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onPostCreated(selectedImageUri, text) }, modifier = Modifier.align(Alignment.End)) {
            Text("發佈")
        }
    }
}
