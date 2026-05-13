@file:OptIn(ExperimentalMaterial3Api::class)

package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import com.muahmmadfadhillaharrobbi0021.studytimer.R
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.SettingsDataStore
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.ThemeColor
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onAboutClick: () -> Unit,
    onCoursesClick: () -> Unit
) {
    val context = LocalContext.current
    val sharedPref = remember {
        context.getSharedPreferences("study_timer_prefs", Context.MODE_PRIVATE)
    }
    var isNotificationEnabled by remember {
        mutableStateOf(sharedPref.getBoolean("notification_enabled", false))
    }

    val dataStore = SettingsDataStore(context)
    val selectedTheme by dataStore.themeFlow.collectAsState(initial = "Cyan")
    val scope = rememberCoroutineScope()

    colorResource(R.color.neon_cyan)
    val darkBackground = colorResource(R.color.dark_background)
    val darkSurface = colorResource(R.color.dark_surface)
    val textPrimary = colorResource(R.color.text_primary)
    val neonBlueDark = colorResource(R.color.neon_blue_dark)

    val accentColor = ThemeColor.fromString(selectedTheme)

    val darkGradient = Brush.verticalGradient(
        colors = listOf(darkBackground, neonBlueDark)
    )
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.menu_settings),
                        fontWeight = FontWeight.ExtraBold,
                        color = accentColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = textPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
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
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile
                Text(
                    text = stringResource(R.string.setting_profile),
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
                SettingItemCard(
                    title = "Muhammad Fadhillah Ar Robbi",
                    subtitle = "NIM: 607062400021",
                    icon = Icons.Default.Person,
                    accentColor = accentColor,
                    darkSurface = darkSurface,
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.setting_theme),
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
                ElevatedCard(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = darkSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(R.string.setting_theme_sub),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ThemeColor.options.forEach { colorName ->
                                val color = ThemeColor.fromString(colorName)
                                val isSelected = selectedTheme == colorName
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .then(
                                            if (isSelected) Modifier.border(
                                                3.dp, Color.White, CircleShape
                                            ) else Modifier
                                        )
                                        .clickable {
                                            scope.launch {
                                                dataStore.saveTheme(colorName)
                                            }
                                        }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.setting_notif_sound),
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
                SettingSwitchCard(
                    title = stringResource(R.string.setting_alarm_test),
                    checked = isNotificationEnabled,
                    onCheckedChange = { newState ->
                        isNotificationEnabled = newState
                        sharedPref.edit { putBoolean("notification_enabled", newState) }
                        if (newState) playTestSound(context)
                    },
                    icon = Icons.Default.Notifications,
                    accentColor = accentColor,
                    darkSurface = darkSurface
                )

                Text(
                    text = stringResource(R.string.setting_others),
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
                SettingItemCard(
                    title = stringResource(R.string.setting_language),
                    subtitle = stringResource(R.string.setting_language_sub),
                    icon = Icons.Default.Language,
                    accentColor = accentColor,
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
                    accentColor = accentColor,
                    darkSurface = darkSurface,
                    onClick = onAboutClick
                )
                SettingItemCard(
                    title = stringResource(R.string.menu_courses),
                    subtitle = stringResource(R.string.courses_empty),
                    icon = Icons.Default.School,
                    accentColor = accentColor,
                    darkSurface = darkSurface,
                    onClick = onCoursesClick
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

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

@Composable
fun SettingItemCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    darkSurface: Color,
    onClick: () -> Unit
) {
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
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    text = subtitle,
                    color = colorResource(R.color.text_secondary),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SettingSwitchCard(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: ImageVector,
    accentColor: Color,
    darkSurface: Color
) {
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
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = accentColor,
                    checkedTrackColor = accentColor.copy(alpha = 0.5f)
                )
            )
        }
    }
}