package com.example.ecloth.ui.theme

import com.example.ecloth.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter







// 2. 修改 sampleRanking 函數，使其回傳 List<Post>
//    並且直接使用 Post 來建立假資料
fun sampleRanking(): List<Post> = listOf(
    Post(1, "Alice", R.drawable.karina, null, "今天的穿搭！"),
    Post(2, "Bob", R.drawable.weather, null, "今天天氣好好 🌞"),
    Post(3, "Cathy", R.drawable.coffee, null, "咖啡廳打卡 ☕"),
    Post(4, "David", R.drawable.karina, null, "第一次發文～"),
)

@Composable
fun LeaderboardScreen(posts: List<Post> = sampleRanking()) { // 參數類型正確
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        val sortedPosts = posts.sortedByDescending { it.likes }
        items(sortedPosts.size) { index ->
            val post = sortedPosts[index]
            LeaderboardItem(post, index + 1)
        }
    }
}

@Composable
fun LeaderboardItem(post: Post, rank: Int) { // 3.  // 将参数类型从 RankedPost 改为 Post

    val rankColor = when(rank) {
        1 -> Color(0xFFFFD700) // 金
        2 -> Color(0xFFC0C0C0) // 银
        3 -> Color(0xFFCD7F32) // 铜
        else -> Color.Gray // 为其他排名设置一个默认颜色
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 排名徽章
            Text(
                // 使用 when 表达式来处理前三名的徽章
                text = when(rank) {
                    1 -> "🥇"
                    2 -> "🥈"
                    3 -> "🥉"
                    else -> "$rank"
                },
                color = rankColor,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 12.dp)
            )

            // 贴文图片
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                if (post.imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(post.imageUri),
                        contentDescription = null
                    )
                } else if (post.imageRes != null) {
                    // 如果有本地图片资源，请确保 R 文件已正确导入
                    // Image(
                    //     painter = painterResource(id = post.imageRes),
                    //     contentDescription = null
                    // )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(post.userName, fontWeight = FontWeight.Bold)

                Text(
                    post.content.take(20) + if (post.content.length > 20) "..." else "",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(18.dp)
                    )
                    Text("${post.likes}", modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
    }
}