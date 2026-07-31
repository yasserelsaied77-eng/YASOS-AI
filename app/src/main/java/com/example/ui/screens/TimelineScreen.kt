package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.model.TimelineEvent
import com.example.core.graph.model.NodeType
import com.example.ui.Routes
import com.example.ui.theme.*
import com.example.ui.viewmodels.TimelineViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    navController: NavController,
    viewModel: TimelineViewModel = viewModel()
) {
    val events by viewModel.events.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "التسلسل الزمني التنفيذي (Timeline)", 
                        color = ExecutivePrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ExecutiveSurfaceDark
                )
            )
        },
        containerColor = ExecutiveBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.LIFE_INBOX) },
                containerColor = ExecutiveCyanDark,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "إضافة مُدخل جديد")
            }
        }
    ) { padding ->
        if (events.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("لا توجد أحداث في التسلسل الزمني حتى الآن.", color = ExecutiveSecondary)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
            ) {
                items(events, key = { it.nodeId }) { event ->
                    TimelineItemRow(event = event)
                }
            }
        }
    }
}

@Composable
fun TimelineItemRow(event: TimelineEvent) {
    val iconAndColor = getEventIconAndColor(event.type)
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Left timeline column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(iconAndColor.second.copy(alpha = 0.15f))
                    .border(1.dp, iconAndColor.second.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconAndColor.first,
                    contentDescription = null,
                    tint = iconAndColor.second,
                    modifier = Modifier.size(16.dp)
                )
            }
            // Timeline connector line
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(100.dp)
                    .background(GlassBorder)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Right content card
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(ExecutiveSurface)
                .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.type.name,
                    color = iconAndColor.second,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(iconAndColor.second.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
                Text(
                    text = formatTime(event.timestamp),
                    color = ExecutiveSecondary,
                    fontSize = 10.sp
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = event.title,
                color = ExecutivePrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = event.summary,
                color = ExecutiveSecondary,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.DeviceHub, contentDescription = null, tint = ExecutiveSecondary, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Relations: ${event.relationCount}", color = ExecutiveSecondary, fontSize = 10.sp)
                
                if (event.connectedNodes.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.Default.Link, contentDescription = null, tint = ExecutiveSecondary, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Connected: ${event.connectedNodes.size}", color = ExecutiveSecondary, fontSize = 10.sp)
                }
            }
        }
    }
}

fun getEventIconAndColor(type: NodeType): Pair<ImageVector, Color> {
    return when (type) {
        NodeType.INBOX -> Pair(Icons.Default.Inbox, ExecutiveCyan)
        NodeType.CONTEXT -> Pair(Icons.Default.AutoAwesome, ExecutivePurple)
        NodeType.DECISION -> Pair(Icons.Default.Gavel, ExecutiveEmerald)
        NodeType.KNOWLEDGE -> Pair(Icons.Default.MenuBook, ExecutiveSky)
        NodeType.PROJECT -> Pair(Icons.Default.AccountTree, ExecutiveAmber)
        NodeType.TASK -> Pair(Icons.Default.CheckCircle, ExecutiveRose)
        NodeType.LESSON -> Pair(Icons.Default.Lightbulb, ExecutiveAmber)
        NodeType.STRATEGY -> Pair(Icons.Default.Explore, ExecutiveBlue)
        NodeType.PERSON -> Pair(Icons.Default.Person, ExecutiveSecondary)
        NodeType.ORGANIZATION -> Pair(Icons.Default.Business, ExecutiveSecondary)
        NodeType.LOCATION -> Pair(Icons.Default.Place, ExecutiveSecondary)
    }
}

fun formatTime(timestamp: Long): String {
    if (timestamp == 0L) return "Unknown"
    val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.US)
    return sdf.format(Date(timestamp))
}
