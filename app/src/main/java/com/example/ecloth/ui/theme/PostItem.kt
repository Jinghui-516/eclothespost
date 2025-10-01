package com.example.ecloth.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun PostItem(post: Post) {
    var liked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(0) }
    var commentText by remember { mutableStateOf(TextFieldValue("")) }
    var comments by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(36.dp).background(Color.Gray, CircleShape))
            Spacer(modifier = Modifier.width(8.dp))
            Text(post.userName)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (post.imageRes != null) {
            Image(painter = painterResource(id = post.imageRes), contentDescription = null, modifier = Modifier.fillMaxWidth().height(300.dp))
        } else if (post.imageUri != null) {
            Image(painter = rememberAsyncImagePainter(post.imageUri), contentDescription = null, modifier = Modifier.fillMaxWidth().height(300.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(post.content)

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = "Like", tint = if (liked) Color.Red else Color.Gray, modifier = Modifier.size(28.dp).clickable {
                liked = !liked
                likeCount += if (liked) 1 else -1
            })
            Spacer(modifier = Modifier.width(8.dp))
            Text("$likeCount 個讚")
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Comment", tint = Color.Gray, modifier = Modifier.size(28.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = commentText, onValueChange = { commentText = it }, placeholder = { Text("留言...") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = {
            if (commentText.text.isNotBlank()) {
                comments = comments + commentText.text
                commentText = TextFieldValue("")
            }
        }, modifier = Modifier.align(Alignment.End)) { Text("送出") }

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
                    Text(text = if (expanded) "收合留言" else "查看更多留言", color = Color.Gray, modifier = Modifier.padding(4.dp).clickable { expanded = !expanded })
                }
            }
        }
    }
}
