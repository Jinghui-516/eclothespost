package com.example.ecloth.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ecloth.R

@Composable
fun PostItem(post: Post) {

    // --- 狀態變數 ---
    var liked by remember { mutableStateOf(false) }            // 是否按讚
    var likeCount by remember { mutableStateOf(0) }            // 讚數
    var commentText by remember { mutableStateOf(TextFieldValue("")) } // 正在輸入的留言
    var comments by remember { mutableStateOf(listOf<String>()) }      // 留言列表
    var expanded by remember { mutableStateOf(false) }          // 留言是否展開

    // --- 整個貼文外層 ---
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        // ----------------------------------------------------
        // 🧑 貼文作者區塊（頭像 + 名字）
        // ----------------------------------------------------
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.Gray, CircleShape)   // 頭像 placeholder
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(post.userName)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ----------------------------------------------------
        // 🖼️ 貼文圖片（支援：Drawable / Uri）
        // ----------------------------------------------------
        if (post.imageRes != null) {
            // Drawable 資源
            Image(
                painter = painterResource(id = post.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        } else if (post.imageUri != null) {
            // 相簿 / 相機 Uri
            Image(
                painter = rememberAsyncImagePainter(post.imageUri),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ----------------------------------------------------
        // ✏️ 文字內容（貼文內容）
        // ----------------------------------------------------
        Text(post.content)

        Spacer(modifier = Modifier.height(8.dp))

        // ----------------------------------------------------
        // ❤️ 讚 & 💬留言 按鈕列
        // ----------------------------------------------------
        Row(verticalAlignment = Alignment.CenterVertically) {

            // ----- 自訂按讚按鈕（衣服/衣架 icon） -----
            Image(
                painter = painterResource(
                    id = if (liked) R.drawable.ic_clothes else R.drawable.ic_hanger
                ),
                contentDescription = "Like",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        liked = !liked
                        likeCount += if (liked) 1 else -1
                    }
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text("$likeCount 個讚")

            Spacer(modifier = Modifier.width(16.dp))

            // ----- 留言 icon（無操作行為） -----
            Icon(
                Icons.Outlined.ChatBubbleOutline,
                contentDescription = "Comment",
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ----------------------------------------------------
        // 📝 留言輸入框
        // ----------------------------------------------------
        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            placeholder = { Text("留言...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ----------------------------------------------------
        // 📤 留言送出按鈕
        // ----------------------------------------------------
        Button(
            onClick = {
                if (commentText.text.isNotBlank()) {
                    comments = comments + commentText.text   // 加到留言列表
                    commentText = TextFieldValue("")         // 清空輸入框
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("送出")
        }

        // ----------------------------------------------------
        // 💬 顯示留言
        // ----------------------------------------------------
        if (comments.isNotEmpty()) {

            // 如果留言超過兩筆，只顯示前兩筆，除非使用者展開
            val displayedComments =
                if (expanded || comments.size <= 2)
                    comments
                else
                    comments.take(2)

            Column {
                displayedComments.forEach { comment ->
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        // 小頭像
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color.LightGray, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        // ○○：留言內容
                        Text("${post.userName}：$comment")
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }

                // 如果留言超過 2 則顯示「查看更多/收合」
                if (comments.size > 2) {
                    Text(
                        text = if (expanded) "收合留言" else "查看更多留言",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { expanded = !expanded }
                    )
                }
            }
        }

        // ----------------------------------------------------
        // 分隔線
        // ----------------------------------------------------
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
