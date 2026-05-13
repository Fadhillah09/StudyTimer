@file:OptIn(ExperimentalMaterial3Api::class)

package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muahmmadfadhillaharrobbi0021.studytimer.R
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.SettingsDataStore
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.ThemeColor
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditSessionScreen(
    sessionId: Long?,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val dataStore = remember { SettingsDataStore(context) }
    val selectedTheme by dataStore.themeFlow.collectAsState(initial = "Cyan")
    val accentColor = ThemeColor.fromString(selectedTheme)

    val factory = ViewModelFactory(context)
    val viewModel: HistoryViewModel = viewModel(factory = factory)

    val modeFocus = stringResource(R.string.mode_focus)
    val modeBreak = stringResource(R.string.mode_break)
    val lowText = stringResource(R.string.con_low)
    val medText = stringResource(R.string.con_med)
    val highText = stringResource(R.string.con_high)
    val categoryList = listOf(
        stringResource(R.string.cat_study),
        stringResource(R.string.cat_assignment),
        stringResource(R.string.cat_others)
    )

    var courseName by rememberSaveable { mutableStateOf("") }
    var selectedMode by rememberSaveable { mutableStateOf(modeFocus) }
    var concentrationLevel by rememberSaveable { mutableFloatStateOf(2f) }
    var originalDuration by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf(categoryList[0]) }

    val expandedCategory: MutableState<Boolean> = remember { mutableStateOf(false) }
    val showDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

    val darkBackground = colorResource(R.color.dark_background)
    val darkSurface = colorResource(R.color.dark_surface)
    val textPrimary = colorResource(R.color.text_primary)
    val neonBlueDark = colorResource(R.color.neon_blue_dark)

    val darkGradient = Brush.verticalGradient(
        colors = listOf(darkBackground, neonBlueDark)
    )

    LaunchedEffect(sessionId) {
        if (sessionId == null) return@LaunchedEffect
        val data = viewModel.getSessionById(sessionId) ?: return@LaunchedEffect
        courseName = data.courseName
        selectedMode = data.mode
        selectedCategory = data.category
        originalDuration = data.duration
        concentrationLevel = when (data.concentration) {
            lowText -> 1f
            highText -> 3f
            else -> 2f
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            containerColor = darkSurface,
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = stringResource(R.string.delete_all_title),
                    color = textPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.delete_all_message),
                    color = textPrimary.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (sessionId != null) {
                            viewModel.delete(sessionId)
                        }
                        showDialog.value = false
                        onBackClick()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text(stringResource(R.string.label_delete), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog.value = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = textPrimary)
                ) {
                    Text(stringResource(R.string.label_cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.edit_session),
                        fontWeight = FontWeight.ExtraBold,
                        color = accentColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.label_back),
                            tint = textPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (courseName.isBlank()) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.invalid_input),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@IconButton
                        }
                        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                        val timestamp = formatter.format(Date())
                        val concentration = when {
                            concentrationLevel < 1.5f -> lowText
                            concentrationLevel < 2.5f -> medText
                            else -> highText
                        }
                        if (sessionId != null) {
                            viewModel.update(
                                id = sessionId,
                                courseName = courseName,
                                mode = selectedMode,
                                concentration = concentration,
                                duration = originalDuration,
                                category = selectedCategory,
                                timestamp = timestamp
                            )
                        }
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.label_save),
                            tint = accentColor
                        )
                    }

                    if (sessionId != null) {
                        IconButton(onClick = { showDialog.value = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.label_delete),
                                tint = Color.Red
                            )
                        }
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = courseName,
                    onValueChange = { courseName = it },
                    label = { Text(stringResource(R.string.label_activity_name), color = accentColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = accentColor
                    )
                )

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

                ExposedDropdownMenuBox(
                    expanded = expandedCategory.value,
                    onExpandedChange = { expandedCategory.value = !expandedCategory.value }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.label_category), color = accentColor) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedCategory.value) },
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

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}