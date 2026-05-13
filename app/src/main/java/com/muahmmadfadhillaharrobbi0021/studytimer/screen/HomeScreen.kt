@file:OptIn(ExperimentalMaterial3Api::class)

package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muahmmadfadhillaharrobbi0021.studytimer.R
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.SettingsDataStore
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.ThemeColor
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.ViewModelFactory

@Composable
fun HomeScreen(
    onStartClick: (String, Int, String, Int, String) -> Unit,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val courseViewModel: CourseViewModel = viewModel(factory = factory)
    val courses by courseViewModel.courses.collectAsState()

    val dataStore = SettingsDataStore(context)
    val selectedTheme by dataStore.themeFlow.collectAsState(initial = "Cyan")
    val accentColor = ThemeColor.fromString(selectedTheme)

    val modeFocus = stringResource(R.string.mode_focus)
    val modeBreak = stringResource(R.string.mode_break)
    val durationList = listOf(
        stringResource(R.string.duration_10s),
        stringResource(R.string.duration_25),
        stringResource(R.string.duration_50),
        stringResource(R.string.duration_90)
    )
    val categoryList = listOf(
        stringResource(R.string.cat_study),
        stringResource(R.string.cat_assignment),
        stringResource(R.string.cat_others)
    )
    val lowText = stringResource(R.string.con_low)
    val medText = stringResource(R.string.con_med)
    val highText = stringResource(R.string.con_high)

    var activityName by rememberSaveable { mutableStateOf("") }
    var selectedMode by rememberSaveable { mutableStateOf(modeFocus) }
    var selectedDuration by rememberSaveable { mutableStateOf(durationList[0]) }
    var selectedCategory by rememberSaveable { mutableStateOf(categoryList[0]) }
    var concentrationLevel by rememberSaveable { mutableFloatStateOf(2f) }

    val expandedCourse: MutableState<Boolean> = remember { mutableStateOf(false) }
    val expandedDuration: MutableState<Boolean> = remember { mutableStateOf(false) }
    val expandedCategory: MutableState<Boolean> = remember { mutableStateOf(false) }

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
                title = {
                    Text(
                        text = stringResource(R.string.title_home),
                        fontWeight = FontWeight.ExtraBold,
                        color = accentColor
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    IconButton(onClick = onAboutClick) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = accentColor
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
                    color = accentColor
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
                        // Course dropdown — pilih dari DB atau ketik manual
                        ExposedDropdownMenuBox(
                            expanded = expandedCourse.value,
                            onExpandedChange = {
                                expandedCourse.value = !expandedCourse.value
                            }
                        ) {
                            OutlinedTextField(
                                value = activityName,
                                onValueChange = { activityName = it },
                                label = {
                                    Text(
                                        text = stringResource(R.string.label_activity_name),
                                        color = textPrimary
                                    )
                                },
                                trailingIcon = {
                                    if (courses.isNotEmpty()) {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expandedCourse.value
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .menuAnchor(MenuAnchorType.PrimaryEditable)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = textPrimary,
                                    unfocusedTextColor = textPrimary,
                                    focusedBorderColor = accentColor,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = accentColor,
                                    focusedTrailingIconColor = accentColor
                                )
                            )
                            if (courses.isNotEmpty()) {
                                ExposedDropdownMenu(
                                    expanded = expandedCourse.value,
                                    onDismissRequest = { expandedCourse.value = false },
                                    modifier = Modifier.background(darkSurface)
                                ) {
                                    courses.forEach { course ->
                                        DropdownMenuItem(
                                            text = { Text(course.name, color = textPrimary) },
                                            onClick = {
                                                activityName = course.name
                                                expandedCourse.value = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // Mode
                        Column {
                            Text(
                                text = stringResource(R.string.label_select_mode),
                                color = accentColor,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedMode == modeFocus,
                                    onClick = { selectedMode = modeFocus },
                                    colors = RadioButtonDefaults.colors(selectedColor = accentColor)
                                )
                                Text(modeFocus, color = textPrimary)
                                Spacer(modifier = Modifier.width(16.dp))
                                RadioButton(
                                    selected = selectedMode == modeBreak,
                                    onClick = { selectedMode = modeBreak },
                                    colors = RadioButtonDefaults.colors(selectedColor = accentColor)
                                )
                                Text(modeBreak, color = textPrimary)
                            }
                        }

                        // Concentration
                        Column {
                            val statusText = when {
                                concentrationLevel < 1.5f -> lowText
                                concentrationLevel < 2.5f -> medText
                                else -> highText
                            }
                            Text(
                                text = "${stringResource(R.string.label_concentration)}: $statusText",
                                color = accentColor,
                                fontWeight = FontWeight.Bold
                            )
                            Slider(
                                value = concentrationLevel,
                                onValueChange = { concentrationLevel = it },
                                valueRange = 1f..3f,
                                steps = 1,
                                colors = SliderDefaults.colors(
                                    thumbColor = accentColor,
                                    activeTrackColor = accentColor
                                )
                            )
                        }

                        // Dropdown Durasi
                        ExposedDropdownMenuBox(
                            expanded = expandedDuration.value,
                            onExpandedChange = {
                                expandedDuration.value = !expandedDuration.value
                            }
                        ) {
                            OutlinedTextField(
                                value = selectedDuration,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(
                                        text = stringResource(R.string.label_duration),
                                        color = accentColor
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expandedDuration.value)
                                },
                                modifier = Modifier
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = textPrimary,
                                    unfocusedTextColor = textPrimary,
                                    focusedBorderColor = accentColor,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = accentColor,
                                    focusedTrailingIconColor = accentColor
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedDuration.value,
                                onDismissRequest = { expandedDuration.value = false },
                                modifier = Modifier.background(darkSurface)
                            ) {
                                durationList.forEach { duration ->
                                    DropdownMenuItem(
                                        text = { Text(duration, color = textPrimary) },
                                        onClick = {
                                            selectedDuration = duration
                                            expandedDuration.value = false
                                        }
                                    )
                                }
                            }
                        }

                        // Dropdown Kategori
                        ExposedDropdownMenuBox(
                            expanded = expandedCategory.value,
                            onExpandedChange = {
                                expandedCategory.value = !expandedCategory.value
                            }
                        ) {
                            OutlinedTextField(
                                value = selectedCategory,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(
                                        text = stringResource(R.string.label_category),
                                        color = accentColor
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expandedCategory.value)
                                },
                                modifier = Modifier
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = textPrimary,
                                    unfocusedTextColor = textPrimary,
                                    focusedBorderColor = accentColor,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = accentColor,
                                    focusedTrailingIconColor = accentColor
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedCategory.value,
                                onDismissRequest = { expandedCategory.value = false },
                                modifier = Modifier.background(darkSurface)
                            ) {
                                categoryList.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(category, color = textPrimary) },
                                        onClick = {
                                            selectedCategory = category
                                            expandedCategory.value = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                androidx.compose.material3.Button(
                    onClick = {
                        val durationInt = selectedDuration.filter { it.isDigit() }.toIntOrNull() ?: 25
                        onStartClick(
                            activityName,
                            durationInt,
                            selectedCategory,
                            concentrationLevel.toInt(),
                            selectedMode
                        )
                    },
                    enabled = activityName.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.btn_start_timer),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
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
                        accentColor = accentColor
                    )
                    SmallMenuCard(
                        title = stringResource(R.string.menu_settings),
                        icon = Icons.Default.Settings,
                        modifier = Modifier.weight(1f),
                        onClick = onSettingsClick,
                        accentColor = accentColor
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun SmallMenuCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier,
    onClick: () -> Unit,
    accentColor: Color
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.dark_surface)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = accentColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}