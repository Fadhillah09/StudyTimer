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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muahmmadfadhillaharrobbi0021.studytimer.R

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val modeFocus = stringResource(R.string.mode_focus)
    val modeBreak = stringResource(R.string.mode_break)
    val durationList = listOf(
        stringResource(R.string.duration_25),
        stringResource(R.string.duration_50),
        stringResource(R.string.duration_90)
    )
    val kategoriList = listOf(
        stringResource(R.string.cat_study),
        stringResource(R.string.cat_assignment),
        stringResource(R.string.cat_others)
    )
    var name by remember { mutableStateOf("") }
    var selectedMode by remember { mutableStateOf(modeFocus) }
    var expandedDuration by remember { mutableStateOf(false) }
    var selectedDuration by remember { mutableStateOf(durationList[0]) }
    var expandedKategori by remember { mutableStateOf(false) }
    var selectedKategori by remember { mutableStateOf(kategoriList[0]) }
    var keteranganLainnya by remember { mutableStateOf("") }
    val neonCyan = Color(0xFF00E5FF)
    val darkBackground = Color(0xFF0A0A0A)
    val darkSurface = Color(0xFF161616)
    val textPrimary = Color.White
    val darkGradient = Brush.verticalGradient(
        colors = listOf(darkBackground, Color(0xFF001020))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.title_home), fontWeight = FontWeight.ExtraBold, color = textPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About App",
                            tint = textPrimary
                        )
                    }
                }
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(darkGradient)
        ) {
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
                ElevatedCard(
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(containerColor = darkSurface),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text(stringResource(R.string.label_name), color = textPrimary) },
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
                            Text(stringResource(R.string.label_select_mode), style = MaterialTheme.typography.labelLarge, color = textPrimary)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedMode == "Focus",
                                    onClick = { selectedMode = "Focus" },
                                    colors = RadioButtonDefaults.colors(selectedColor = neonCyan, unselectedColor = Color.Gray)
                                )
                                Text(stringResource(R.string.mode_focus), color = textPrimary)
                                Spacer(Modifier.width(16.dp))
                                RadioButton(
                                    selected = selectedMode == "Break",
                                    onClick = { selectedMode = "Break" },
                                    colors = RadioButtonDefaults.colors(selectedColor = neonCyan, unselectedColor = Color.Gray)
                                )
                                Text(stringResource(R.string.mode_break), color = textPrimary)
                            }
                        }
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
                                    unfocusedTextColor = textPrimary,
                                    focusedBorderColor = neonCyan,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = neonCyan,
                                    unfocusedLabelColor = Color.Gray,
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
                                        onClick = {
                                            selectedDuration = duration
                                            expandedDuration = false
                                        }
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
                                    unfocusedTextColor = textPrimary,
                                    focusedBorderColor = neonCyan,
                                    unfocusedBorderColor = Color.Gray,
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
                                        onClick = {
                                            selectedKategori = kategori
                                            expandedKategori = false
                                        }
                                    )
                                }
                            }
                        }
                        if (selectedKategori == stringResource(R.string.cat_others)) {
                            OutlinedTextField(
                                value = keteranganLainnya,
                                onValueChange = { keteranganLainnya = it },
                                label = { Text(stringResource(R.string.label_other_desc), color = textPrimary) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = textPrimary,
                                    unfocusedTextColor = textPrimary,
                                    focusedBorderColor = neonCyan,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = neonCyan
                                )
                            )
                        }
                    }
                }
                Button(
                    onClick = onStartClick,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = neonCyan, contentColor = Color.Black),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.btn_start_timer), fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF161616))
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