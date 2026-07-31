package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.*

import com.example.ui.screens.CaptureScreen
import com.example.ui.screens.TimelineScreen

// --- Navigation Routes ---
object Routes {
    const val HOME = "home"
    const val DOMAINS_CENTER = "domains"
    const val COMMUNICATIONS = "communications"
    const val SETTINGS = "settings"
    const val EXECUTIVE_DASHBOARD = "dashboard"
    const val LIFE_INBOX = "inbox"
    const val AI_DIRECTOR = "director"
    const val MEMORY_VAULT = "memory"
    const val EXECUTION_CENTER = "execution"
    const val STRATEGY_CENTER = "strategy"
    const val PHARMACY_DOMAIN = "pharmacy"
}

@Composable
fun YasosApp() {
    // Force RTL for Arabic First
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            containerColor = ExecutiveBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { padding ->
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                NavHost(navController = navController, startDestination = Routes.HOME) {
                    composable(Routes.HOME) { TimelineScreen(navController) }
                    composable(Routes.DOMAINS_CENTER) { PlaceholderScreen("المجالات (Domains)", navController) }
                    composable(Routes.COMMUNICATIONS) { PlaceholderScreen("التواصل (Communications)", navController) }
                    composable(Routes.SETTINGS) { PlaceholderScreen("الإعدادات (Settings)", navController) }
                    
                    // Internal routes
                    composable(Routes.EXECUTIVE_DASHBOARD) { PlaceholderScreen("لوحة القيادة التنفيذية", navController) }
                    composable(Routes.LIFE_INBOX) { CaptureScreen(navController) }
                    composable(Routes.AI_DIRECTOR) { PlaceholderScreen("المدير الذكي", navController) }
                    composable(Routes.MEMORY_VAULT) { PlaceholderScreen("خزنة الذاكرة", navController) }
                    composable(Routes.EXECUTION_CENTER) { PlaceholderScreen("مركز التنفيذ", navController) }
                    composable(Routes.STRATEGY_CENTER) { PlaceholderScreen("الاستراتيجية", navController) }
                    composable(Routes.PHARMACY_DOMAIN) { PlaceholderScreen("الصيدلة", navController) }
                }
            }
        }
    }
}

@Composable
fun TopNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ExecutiveSurfaceDark)
            .border(1.dp, GlassBorder) // border-b in tailwind, simplified here to full border or omit bottom
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.topRightInLinear(
                            colorStops = arrayOf(
                                0.0f to ExecutiveCyanDark,
                                1.0f to ExecutiveBlue
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("Y", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Column {
                Text(
                    "YASOS",
                    color = ExecutivePrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    "Executive OS v1.0",
                    color = ExecutiveSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
            }
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF0F172A), CircleShape) // slate-900 equivalent
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF94A3B8), modifier = Modifier.size(20.dp))
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, GlassBorder, CircleShape)
                    .background(Color(0x800F172A), CircleShape), // slate-900/50
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(32.dp).background(Color(0xFF334155), CircleShape))
            }
        }
    }
}

fun Brush.Companion.topRightInLinear(vararg colorStops: Pair<Float, Color>) = linearGradient(
    colorStops = colorStops,
    start = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, 0f),
    end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
)

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ExecutiveBackground)
    ) {
        TopNavigationBar()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Executive Dashboard Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "القرارات المعلقة",
                    value = "12",
                    valueColor = ExecutiveCyan,
                    subtitle = "تنبيه استراتيجي"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "مؤشر الطاقة",
                    value = "84%",
                    valueColor = ExecutiveEmerald,
                    subtitle = "أداء تنفيذي"
                )
            }

            // AI Director Quick Capture
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF0F172A), Color(0xFF111111)) // from slate-900 to #111
                        )
                    )
                    .border(1.dp, Color(0x4D164E63), RoundedCornerShape(24.dp)) // cyan-900/30
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                            Text(
                                text = "المدير التنفيذي الذكي",
                                color = Color(0xFFECFEFF), // cyan-50
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "بناءً على بروتوكول DNA الخاص بك، يوصى بمراجعة مشروع \"توسعة الصيدليات\" الآن.",
                                color = ExecutiveSecondary,
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x1A06B6D4)) // cyan-500/10
                                .border(1.dp, Color(0x3306B6D4), RoundedCornerShape(12.dp)), // cyan-500/20
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = ExecutiveCyan, modifier = Modifier.size(20.dp))
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { navController.navigate(Routes.LIFE_INBOX) },
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GlassBackground,
                                contentColor = ExecutivePrimary
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorder),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Capture Input", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        }
                        Button(
                            onClick = { navController.navigate(Routes.AI_DIRECTOR) },
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ExecutiveCyanDark,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("CEO Mode", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Domains & Centers Grid
            val modules = listOf(
                ModuleItem("صندوق الحياة", "📥", Routes.LIFE_INBOX, ExecutiveBlue),
                ModuleItem("مستودع الذاكرة", "🏛️", Routes.MEMORY_VAULT, ExecutivePurple),
                ModuleItem("الاستراتيجية", "📉", Routes.STRATEGY_CENTER, ExecutiveAmber),
                ModuleItem("مركز التنفيذ", "⚡", Routes.EXECUTION_CENTER, ExecutiveEmerald),
                ModuleItem("الصيدلة", "💊", Routes.PHARMACY_DOMAIN, ExecutiveRose),
                ModuleItem("المجالات", "🌐", Routes.DOMAINS_CENTER, ExecutiveSky)
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(modules) { module ->
                    ModuleCard(module) {
                        navController.navigate(module.route)
                    }
                }
            }

            // Quick Capture Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ExecutiveSurface)
                    .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(ExecutiveError)
                )
                Text(
                    text = "سجل فكرة أو ملاحظة صوتية...",
                    color = ExecutiveSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E293B)) // slate-800
                        .clickable { navController.navigate(Routes.LIFE_INBOX) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Mic, contentDescription = "Record", tint = ExecutivePrimary, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String, valueColor: Color, subtitle: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(ExecutiveSurface)
            .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Text(
            text = title,
            color = ExecutiveSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = value,
                color = valueColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = subtitle,
                color = ExecutiveSecondary,
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

data class ModuleItem(val title: String, val emoji: String, val route: String, val color: Color)

@Composable
fun ModuleCard(module: ModuleItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(ExecutiveSurface)
            .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(module.color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = module.emoji, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = module.title,
            color = ExecutivePrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(ExecutiveSurfaceDark)
            .border(1.dp, GlassBorder) // border-t
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "الرئيسية",
            selected = currentRoute == Routes.HOME,
            onClick = { navController.navigate(Routes.HOME) }
        )
        BottomNavItem(
            icon = Icons.Default.GridView,
            label = "المجالات",
            selected = currentRoute == Routes.DOMAINS_CENTER,
            onClick = { navController.navigate(Routes.DOMAINS_CENTER) }
        )
        BottomNavItem(
            icon = Icons.AutoMirrored.Filled.Chat, // Using chat as fallback for communications
            label = "تواصل",
            selected = currentRoute == Routes.COMMUNICATIONS,
            onClick = { navController.navigate(Routes.COMMUNICATIONS) }
        )
        BottomNavItem(
            icon = Icons.Default.Settings,
            label = "الإعدادات",
            selected = currentRoute == Routes.SETTINGS,
            onClick = { navController.navigate(Routes.SETTINGS) }
        )
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, label: String, selected: Boolean, onClick: () -> Unit) {
    val contentColor = if (selected) ExecutiveCyan else ExecutiveSecondary
    val alpha = if (selected) 1f else 0.4f

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .height(32.dp)
                .width(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (selected) Color(0x66164E63) else Color.Transparent), // cyan-900/40
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            color = contentColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderScreen(title: String, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, color = ExecutivePrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = ExecutivePrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ExecutiveSurfaceDark
                )
            )
        },
        containerColor = ExecutiveBackground
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "قريباً...",
                style = MaterialTheme.typography.headlineMedium,
                color = ExecutiveSecondary
            )
        }
    }
}

