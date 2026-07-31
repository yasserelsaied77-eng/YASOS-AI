package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.viewmodels.CaptureViewModel
import com.example.ui.viewmodels.CaptureState
import androidx.compose.material3.CircularProgressIndicator
import com.example.core.model.ExecutiveContext
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureScreen(
    navController: NavController,
    viewModel: CaptureViewModel = viewModel()
) {
    var captureText by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val recentCaptures by viewModel.recentCaptures.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "الالتقاط التنفيذي (Capture)", 
                        color = ExecutivePrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع", tint = ExecutivePrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ExecutiveSurfaceDark
                )
            )
        },
        containerColor = ExecutiveBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Start Voice Capture */ },
                containerColor = ExecutiveCyanDark,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Mic, contentDescription = "تسجيل صوتي")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp, start = 16.dp, end = 16.dp)
        ) {
            // 1. Search / Quick Text Input
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(ExecutiveSurface)
                        .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = captureText,
                        onValueChange = { captureText = it },
                        placeholder = { 
                            Text("ما الذي تفكر فيه؟ أضف فكرة، قرار، أو ملاحظة...", color = ExecutiveSecondary, fontSize = 12.sp) 
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = ExecutivePrimary,
                            unfocusedTextColor = ExecutivePrimary,
                            cursorColor = ExecutiveCyan
                        ),
                        modifier = Modifier.weight(1f),
                        maxLines = 3
                    )
                    
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (captureText.isNotBlank() && uiState !is CaptureState.Processing) ExecutiveCyan else Color(0xFF1E293B))
                            .clickable(enabled = captureText.isNotBlank() && uiState !is CaptureState.Processing) {
                                viewModel.processInput(captureText)
                                captureText = ""
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState is CaptureState.Processing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = ExecutivePrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                Icons.Default.Send, 
                                contentDescription = "إرسال إلى الموجه", 
                                tint = if (captureText.isNotBlank()) Color.White else ExecutiveSecondary, 
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // 2. Quick Actions Grid
            item {
                Column {
                    SectionTitle("إجراءات سريعة")
                    Spacer(modifier = Modifier.height(12.dp))
                    val actions = listOf(
                        CaptureAction("نص", Icons.Default.Edit, ExecutiveBlue),
                        CaptureAction("صوت", Icons.Default.Mic, ExecutiveRose),
                        CaptureAction("كاميرا", Icons.Default.CameraAlt, ExecutiveEmerald),
                        CaptureAction("مستند", Icons.Default.Description, ExecutiveAmber),
                        CaptureAction("رابط", Icons.Default.Link, ExecutiveCyan),
                        CaptureAction("بريد", Icons.Default.Email, ExecutivePurple),
                        CaptureAction("اجتماع", Icons.Default.Groups, ExecutiveSky),
                        CaptureAction("حافظة", Icons.Default.ContentPaste, ExecutiveSecondary)
                    )
                    
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.heightIn(max = 200.dp) // Constrain height inside LazyColumn
                    ) {
                        items(actions) { action ->
                            ActionItem(action)
                        }
                    }
                }
            }

            // 3. AI Analysis & Processing Status
            item {
                Column {
                    SectionTitle("التحليل الذكي (AI Analysis)")
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    when (uiState) {
                        is CaptureState.Processing -> {
                            AnalysisStatusCard(
                                status = "Routing",
                                title = "مُدخل قيد المعالجة...",
                                subtitle = "يتم التحليل بواسطة Executive Router",
                                color = ExecutiveAmber
                            )
                        }
                        is CaptureState.Success -> {
                            val context = (uiState as CaptureState.Success).context
                            AnalysisStatusCard(
                                status = "Processed",
                                title = context.summary,
                                subtitle = "تم التوجيه إلى: ${context.domain.joinToString()}",
                                color = ExecutiveEmerald
                            )
                        }
                        is CaptureState.Error -> {
                            val error = (uiState as CaptureState.Error).message
                            AnalysisStatusCard(
                                status = "Failed",
                                title = "حدث خطأ أثناء المعالجة",
                                subtitle = error,
                                color = ExecutiveRose
                            )
                        }
                        else -> {
                            // Show placeholder or previous results
                            if (recentCaptures.isNotEmpty()) {
                                val latest = recentCaptures.first()
                                AnalysisStatusCard(
                                    status = "Processed",
                                    title = latest.summary,
                                    subtitle = "تم التوجيه إلى: ${latest.domain.joinToString()}",
                                    color = ExecutiveEmerald
                                )
                            } else {
                                Text("لا يوجد مهام قيد المعالجة حالياً", color = ExecutiveSecondary, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // 4. Executive Inbox (Recent Inputs)
            if (recentCaptures.isNotEmpty()) {
                item {
                    Column {
                        SectionTitle("صندوق الوارد التنفيذي")
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        recentCaptures.forEach { ctx ->
                            InboxItem(
                                icon = Icons.Default.Description, // Placeholder icon
                                iconColor = ExecutiveCyan,
                                title = ctx.summary,
                                time = SimpleDateFormat("HH:mm", Locale.US).format(ctx.createdAt),
                                preview = ctx.normalizedInput
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            } else {
                item {
                    Column {
                        SectionTitle("صندوق الوارد التنفيذي")
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        InboxItem(
                            icon = Icons.Default.VoiceChat,
                            iconColor = ExecutiveRose,
                            title = "ملاحظة صوتية سريعة",
                            time = "منذ 10 دقائق",
                            preview = "يجب مراجعة أرقام المبيعات للربع الثالث مع فريق الصيدلة..."
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = ExecutivePrimary,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
    )
}

data class CaptureAction(val title: String, val icon: ImageVector, val color: Color)

@Composable
fun ActionItem(action: CaptureAction) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable { /* TODO */ }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(ExecutiveSurface)
                .border(1.dp, GlassBorder, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(action.icon, contentDescription = action.title, tint = action.color, modifier = Modifier.size(24.dp))
        }
        Text(text = action.title, color = ExecutiveSecondary, fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun AnalysisStatusCard(status: String, title: String, subtitle: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ExecutiveSurface)
            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = ExecutivePrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, color = ExecutiveSecondary, fontSize = 10.sp)
        }
        Text(
            text = status,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(color.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun InboxItem(icon: ImageVector, iconColor: Color, title: String, time: String, preview: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = title, color = ExecutivePrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Text(text = time, color = ExecutiveSecondary, fontSize = 10.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = preview, color = ExecutiveSecondary, fontSize = 11.sp, maxLines = 2, lineHeight = 16.sp)
        }
    }
}
