@file:OptIn(ExperimentalMaterial3Api::class)

package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muahmmadfadhillaharrobbi0021.studytimer.R

@Composable
fun HomeScreen(
    onStartClick: (String, Int, String, Int, String) -> Unit,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    val modeFocus = stringResource(R.string.mode_focus)
    val modeBreak = stringResource(R.string.mode_break)
    val durationList = listOf(
        stringResource(R.string.duration_10s),
        stringResource(R.string.duration_25),
        stringResource(R.string.duration_50),
        stringResource(R.string.duration_90)
    )
    val kategoriList = listOf(
        stringResource(R.string.cat_study),
        stringResource(R.string.cat_assignment),
        stringResource(R.string.cat_others)
    )

    val lowText = stringResource(R.string.con_low)
    val medText = stringResource(R.string.con_med)
    val highText = stringResource(R.string.con_high)

    var activityName by rememberSaveable { mutableStateOf("") }
    var selectedMode by rememberSaveable { mutableStateOf(modeFocus) }
    var expandedDuration by rememberSaveable { mutableStateOf(false) }
    var selectedDuration by rememberSaveable { mutableStateOf(durationList[0]) }
    var expandedKategori by rememberSaveable { mutableStateOf(false) }
    var selectedKategori by rememberSaveable { mutableStateOf(kategoriList[0]) }
    var concentrationLevel by rememberSaveable { mutableFloatStateOf(2f) }

    val neonCyan = colorResource(R.color.neon_cyan)
    val darkBackground = colorResource(R.color.dark_background)
    val darkSurface = colorResource(R.color.dark_surface)
    val neonBlueDark = colorResource(R.color.neon_blue_dark)
    val textPrimary = colorResource(R.color.text_primary)

    val darkGradient = Brush.verticalGradient(
        colors = listOf(darkBackground, neonBlueDark)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.title_home), fontWeight = FontWeight.ExtraBold, color = textPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = onAboutClick) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = textPrimary)
                    }
                }
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().background(darkGradient)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_neon_timer),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = stringResource(R.string.welcome_header),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = neonCyan
                )

                // Card Form Input
                ElevatedCard(
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(containerColor = darkSurface),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = activityName,
                            onValueChange = { activityName = it },
                            label = { Text(stringResource(R.string.label_activity_name), color = textPrimary) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textPrimary,
                                unfocusedTextColor = textPrimary,
                                focusedBorderColor = neonCyan,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = neonCyan
                            )
                        )
                        Column {
                            Text(stringResource(R.string.label_select_mode), color = textPrimary, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedMode == modeFocus,
                                    onClick = { selectedMode = modeFocus },
                                    colors = RadioButtonDefaults.colors(selectedColor = neonCyan)
                                )
                                Text(modeFocus, color = textPrimary)
                                Spacer(Modifier.width(16.dp))
                                RadioButton(
                                    selected = selectedMode == modeBreak,
                                    onClick = { selectedMode = modeBreak },
                                    colors = RadioButtonDefaults.colors(selectedColor = neonCyan)
                                )
                                Text(modeBreak, color = textPrimary)
                            }
                        }
                        Column {
                            val statusText = when {
                                concentrationLevel < 1.5f -> lowText
                                concentrationLevel < 2.5f -> medText
                                else -> highText
                            }
                            Text(
                                text = "${stringResource(R.string.label_concentration)}: $statusText",
                                color = textPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Slider(
                                value = concentrationLevel,
                                onValueChange = { concentrationLevel = it },
                                valueRange = 1f..3f,
                                steps = 1,
                                colors = SliderDefaults.colors(thumbColor = neonCyan, activeTrackColor = neonCyan)
                            )
                        }

                        // Dropdown Durasi
                        ExposedDropdownMenuBox(
                            expanded = expandedDuration,
                            onExpandedChange = { expandedDuration = !expandedDuration }
                        ) {
                            OutlinedTextField(
                                value = selectedDuration,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(stringResource(R.string.label_duration), color = textPrimary) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedDuration) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = textPrimary,
                                    focusedBorderColor = neonCyan,
                                    focusedLabelColor = neonCyan,
                                    focusedTrailingIconColor = neonCyan
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedDuration,
                                onDismissRequest = { expandedDuration = false },
                                modifier = Modifier.background(darkSurface)
                            ) {
                                durationList.forEach { duration ->
                                    DropdownMenuItem(
                                        text = { Text(duration, color = textPrimary) },
                                        onClick = { selectedDuration = duration; expandedDuration = false }
                                    )
                                }
                            }
                        }
                        ExposedDropdownMenuBox(
                            expanded = expandedKategori,
                            onExpandedChange = { expandedKategori = !expandedKategori }
                        ) {
                            OutlinedTextField(
                                value = selectedKategori,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(stringResource(R.string.label_category), color = textPrimary) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedKategori) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = textPrimary,
                                    focusedBorderColor = neonCyan,
                                    focusedLabelColor = neonCyan,
                                    focusedTrailingIconColor = neonCyan
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedKategori,
                                onDismissRequest = { expandedKategori = false },
                                modifier = Modifier.background(darkSurface)
                            ) {
                                kategoriList.forEach { kategori ->
                                    DropdownMenuItem(
                                        text = { Text(kategori, color = textPrimary) },
                                        onClick = { selectedKategori = kategori; expandedKategori = false }
                                    )
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        val durationInt = selectedDuration.filter { it.isDigit() }.toIntOrNull() ?: 25
                        onStartClick(activityName, durationInt, selectedKategori, concentrationLevel.toInt(), selectedMode)
                    },
                    enabled = activityName.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = neonCyan, contentColor = Color.Black)
                ) {
                    Icon(Icons.Default.PlayArrow, null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.btn_start_timer), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SmallMenuCard(
                        title = stringResource(R.string.menu_history),
                        icon = Icons.Default.History,
                        modifier = Modifier.weight(1f),
                        onClick = onHistoryClick,
                        accentColor = neonCyan
                    )
                    SmallMenuCard(
                        title = stringResource(R.string.menu_settings),
                        icon = Icons.Default.Settings,
                        modifier = Modifier.weight(1f),
                        onClick = onSettingsClick,
                        accentColor = neonCyan
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallMenuCard(title: String, icon: ImageVector, modifier: Modifier, onClick: () -> Unit, accentColor: Color) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = colorResource(R.color.dark_surface))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, null, tint = accentColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}