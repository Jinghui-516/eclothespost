package com.example.ecloth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecloth.ui.theme.Beige
import com.example.ecloth.ui.theme.DeepBrown
import com.example.ecloth.ui.theme.GrayLine
import com.example.ecloth.ui.theme.MilkBrown

// ------------------------------------------------------
// 📌 穿搭風格資料類別
// ------------------------------------------------------
data class StyleTag(
    val id: Int,
    val name: String,
    val icon: ImageVector
)

// ------------------------------------------------------
// 📌 30 種穿搭風格（不需 drawable）
// ------------------------------------------------------
val styleTagList = listOf(
    StyleTag(1, "休閒", Icons.Outlined.Checkroom),
    StyleTag(2, "街頭", Icons.Outlined.DirectionsWalk),
    StyleTag(3, "正式", Icons.Outlined.BusinessCenter),
    StyleTag(4, "運動", Icons.Outlined.FitnessCenter),
    StyleTag(5, "復古", Icons.Outlined.AutoAwesome),
    StyleTag(6, "極簡", Icons.Outlined.CropSquare),
    StyleTag(7, "韓系", Icons.Outlined.Face),
    StyleTag(8, "日系", Icons.Outlined.EmojiPeople),
    StyleTag(9, "可愛", Icons.Outlined.FavoriteBorder),
    StyleTag(10, "酷帥", Icons.Outlined.AcUnit),
    StyleTag(11, "優雅", Icons.Outlined.Diamond),
    StyleTag(12, "性感", Icons.Outlined.Whatshot),
    StyleTag(13, "日常", Icons.Outlined.Today),
    StyleTag(14, "上班族", Icons.Outlined.WorkOutline),
    StyleTag(15, "丹寧", Icons.Outlined.LocalLaundryService),
    StyleTag(16, "黑白", Icons.Outlined.Tonality),
    StyleTag(17, "層次穿搭", Icons.Outlined.Layers),
    StyleTag(18, "奢華", Icons.Outlined.StarBorder),
    StyleTag(19, "戶外", Icons.Outlined.Terrain),
    StyleTag(20, "Y2K", Icons.Outlined.Bolt),
    StyleTag(21, "中性", Icons.Outlined.Male),
    StyleTag(22, "甜美", Icons.Outlined.Female),
    StyleTag(23, "嘻哈", Icons.Outlined.Headphones),
    StyleTag(24, "寬鬆", Icons.Outlined.OpenInFull),
    StyleTag(25, "懷舊", Icons.Outlined.History),
    StyleTag(26, "柔和", Icons.Outlined.Cloud),
    StyleTag(27, "暗黑", Icons.Outlined.DarkMode),
    StyleTag(28, "亮色", Icons.Outlined.WbSunny),
    StyleTag(29, "簡約", Icons.Outlined.Remove),
    StyleTag(30, "時尚", Icons.Outlined.Style)
)


// ------------------------------------------------------
// 📌 個人檔案頁面
// ------------------------------------------------------
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

    var selectedStyles by remember { mutableStateOf<List<StyleTag>>(emptyList()) }
    var showStylePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // ---------- 頭貼 + e-fit ----------
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "e-fit",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBrown.copy(alpha = 0.3f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ---------- 暱稱 ----------
        Text(
            text = "USER_NAME",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
            color = DeepBrown
        )

        // ---------- 穿搭風格 ----------
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            selectedStyles.forEach {
                Icon(
                    imageVector = it.icon,
                    contentDescription = it.name,
                    tint = DeepBrown,
                    modifier = Modifier.size(26.dp)
                )
            }

            IconButton(onClick = { showStylePicker = true }) {
                Icon(
                    imageVector = Icons.Outlined.AddCircleOutline,
                    contentDescription = "Add Style",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- 統計 ----------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("貼文", "100")
            StatItem("追蹤", "120")
            StatItem("粉絲", "300")
        }

        Divider(
            color = GrayLine,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        // ---------- 貼文九宮格 ----------
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(sampleImages) { image ->
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(2.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    // ---------- 風格選擇 Dialog ----------
    if (showStylePicker) {
        StylePickerDialog(
            selectedStyles = selectedStyles,
            onStyleClick = { style ->
                selectedStyles =
                    if (selectedStyles.contains(style)) {
                        selectedStyles - style
                    } else if (selectedStyles.size < 3) {
                        selectedStyles + style
                    } else selectedStyles
            },
            onDismiss = { showStylePicker = false }
        )
    }
}

// ------------------------------------------------------
// 📌 統計小元件
// ------------------------------------------------------
@Composable
fun StatItem(title: String, count: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontSize = 16.sp, color = DeepBrown)
        Text(text = title, fontSize = 13.sp, color = Color.Gray)
    }
}

// ------------------------------------------------------
// 📌 穿搭風格選擇 Dialog
// ------------------------------------------------------
@Composable
fun StylePickerDialog(
    selectedStyles: List<StyleTag>,
    onStyleClick: (StyleTag) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text("選擇穿搭風格（最多 3 個）") },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(styleTagList) { style ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selectedStyles.contains(style))
                                    MilkBrown.copy(alpha = 0.2f)
                                else Color.Transparent
                            )
                            .clickable { onStyleClick(style) }
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = style.icon,
                            contentDescription = style.name,
                            modifier = Modifier.size(32.dp),
                            tint = DeepBrown
                        )
                        Text(style.name, fontSize = 12.sp)
                    }
                }
            }
        }
    )
}
