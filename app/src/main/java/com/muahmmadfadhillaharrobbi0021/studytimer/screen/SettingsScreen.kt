@file:OptIn(ExperimentalMaterial3Api::class)

package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muahmmadfadhillaharrobbi0021.studytimer.R

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var isNotificationEnabled by remember { mutableStateOf(false) }
    var isVibrationEnabled by remember { mutableStateOf(false) }
    val neonCyan = colorResource(R.color.neon_cyan)
    val darkBackground = colorResource(R.color.dark_background)
    val darkSurface = colorResource(R.color.dark_surface)
    val textPrimary = colorResource(R.color.text_primary)
    val neonBlueDark = colorResource(R.color.neon_blue_dark)
    val darkGradient = Brush.verticalGradient(
        colors = listOf(darkBackground, neonBlueDark)
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.menu_settings), fontWeight = FontWeight.ExtraBold, color = textPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = textPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(darkGradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(stringResource(R.string.setting_profile), color = neonCyan, fontWeight = FontWeight.Bold)
                SettingItemCard(
                    title = "Muhammad Fadhillah Ar Robbi",
                    subtitle = "NIM: 607062400021",
                    icon = Icons.Default.Person,
                    neonCyan = neonCyan,
                    darkSurface = darkSurface,
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.setting_notif_sound), color = neonCyan, fontWeight = FontWeight.Bold)
                SettingSwitchCard(
                    title = stringResource(R.string.setting_alarm_test),
                    checked = isNotificationEnabled,
                    onCheckedChange = {
                        isNotificationEnabled = it
                        if (it) playTestSound(context)
                    },
                    icon = Icons.Default.Notifications,
                    neonCyan = neonCyan,
                    darkSurface = darkSurface
                )
                Text(stringResource(R.string.setting_others), color = neonCyan, fontWeight = FontWeight.Bold)
                SettingItemCard(
                    title = stringResource(R.string.setting_language),
                    subtitle = stringResource(R.string.setting_language_sub),
                    icon = Icons.Default.Language,
                    neonCyan = neonCyan,
                    darkSurface = darkSurface,
                    onClick = {
                        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                        context.startActivity(intent)
                    }
                )
                SettingItemCard(
                    title = stringResource(R.string.setting_about),
                    subtitle = stringResource(R.string.setting_version),
                    icon = Icons.Default.Info,
                    neonCyan = neonCyan,
                    darkSurface = darkSurface,
                    onClick = { /* Dialog About */ }
                )
            }
        }
    }
}

// FUNGSI BUNYI
fun playTestSound(context: Context) {
    try {
        val mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI)
        mediaPlayer?.let {
            it.start()
            it.setOnCompletionListener { mp -> mp.release() }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItemCard(title: String, subtitle: String, icon: ImageVector, neonCyan: Color, darkSurface: Color, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = darkSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = neonCyan, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(subtitle, color = colorResource(R.color.text_secondary), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun SettingSwitchCard(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, icon: ImageVector, neonCyan: Color, darkSurface: Color) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = darkSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = neonCyan, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = neonCyan,
                    checkedTrackColor = neonCyan.copy(alpha = 0.5f)
                )
            )
        }
    }
}