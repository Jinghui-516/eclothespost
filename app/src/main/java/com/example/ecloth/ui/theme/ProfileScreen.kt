package com.example.ecloth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecloth.ui.theme.MilkBrown
import com.example.ecloth.ui.theme.Beige
import com.example.ecloth.ui.theme.DeepBrown
import com.example.ecloth.ui.theme.GrayLine

@Composable
fun ProfileScreen() {
    val sampleImages = listOf(
        R.drawable.sample1,
        R.drawable.sample2,
        R.drawable.sample3,
        R.drawable.sample4,
        R.drawable.sample5,
        R.drawable.sample6
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige)
            .padding(horizontal = 16.dp)
    ) {


        // 個人頭像與名稱
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "USER_NAME", color = DeepBrown, fontSize = 18.sp)
            Text(text = "VISIT  |  SHARE", color = Color.Gray, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 統計列
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatItem("貼文", "100")
            StatItem("粉絲", "100")
            StatItem("追蹤", "100")
            StatItem("互相追蹤", "100")
        }

        Divider(
            color = GrayLine,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        // 貼文圖片區
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(sampleImages) { image ->
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(2.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }


    }
}


@Composable
fun StatItem(title: String, count: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontSize = 16.sp, color = DeepBrown)
        Text(text = title, fontSize = 13.sp, color = Color.Gray)
    }
}
@Composable
fun BottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MilkBrown, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = Color.White)
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Camera", tint = Color.White)
        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
    }
}










