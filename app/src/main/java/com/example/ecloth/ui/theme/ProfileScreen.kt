package com.example.ecloth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 頭像
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "USER_NAME", style = MaterialTheme.typography.titleMedium)
        Text(text = "VISIT | SHARE", color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // 統計數據
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatItem("貼文", "100")
            StatItem("粉絲", "100")
            StatItem("追蹤", "100")
            StatItem("互相追蹤", "100")
        }
    }
}

@Composable
fun StatItem(title: String, count: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, style = MaterialTheme.typography.bodyLarge)
        Text(text = title, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}
