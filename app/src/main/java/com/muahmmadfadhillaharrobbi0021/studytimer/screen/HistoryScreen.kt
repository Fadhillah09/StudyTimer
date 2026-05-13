@file:OptIn(ExperimentalMaterial3Api::class)

package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muahmmadfadhillaharrobbi0021.studytimer.R
import com.muahmmadfadhillaharrobbi0021.studytimer.model.Session
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.SettingsDataStore
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.ThemeColor
import com.muahmmadfadhillaharrobbi0021.studytimer.utils.ViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    onBackClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onRecycleBinClick: () -> Unit
) {
    val context = LocalContext.current
    val dataStore = remember { SettingsDataStore(context) }
    val selectedTheme by dataStore.themeFlow.collectAsState(initial = "Cyan")
    val accentColor = ThemeColor.fromString(selectedTheme)

    val viewModel: HistoryViewModel = viewModel(factory = ViewModelFactory(context))
    val sessions by viewModel.sessions.collectAsState()

    val showList by dataStore.layoutFlow.collectAsState(initial = true)
    val scope = rememberCoroutineScope()

    val neonCyan = colorResource(R.color.neon_cyan)
    val darkBackground = colorResource(R.color.dark_background)
    val textPrimary = colorResource(R.color.text_primary)
    val neonBlueDark = colorResource(R.color.neon_blue_dark)
    val darkSurface = colorResource(R.color.dark_surface)

    val darkGradient = Brush.verticalGradient(
        colors = listOf(darkBackground, neonBlueDark)
    )

    val showDeleteAllDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

    if (showDeleteAllDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteAllDialog.value = false },
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
                        viewModel.moveAllToRecycleBin()
                        showDeleteAllDialog.value = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text(stringResource(R.string.label_delete), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteAllDialog.value = false },
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
                        text = stringResource(R.string.menu_history),
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
                actions = {
                    IconButton(onClick = {
                        scope.launch { dataStore.saveLayout(!showList) }
                    }) {
                        Icon(
                            imageVector = if (showList) Icons.Default.GridView
                            else Icons.AutoMirrored.Filled.ViewList,
                            contentDescription = null,
                            tint = accentColor
                        )
                    }

                    IconButton(onClick = onRecycleBinClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.recycle_bin),
                            tint = Color.Gray
                        )
                    }

                    if (sessions.isNotEmpty()) {
                        IconButton(onClick = { showDeleteAllDialog.value = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
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
                .padding(paddingValues)
        ) {
            if (sessions.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.history_empty),
                        color = Color.Gray
                    )
                }
            } else {
                if (showList) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(sessions) { session ->
                            HistoryCard(
                                session = session,
                                cardColor = darkSurface,
                                accentColor = neonCyan,
                                onClick = { onItemClick(session.id) }
                            )
                        }
                    }
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalItemSpacing = 10.dp,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(sessions) { session ->
                            HistoryGridCard(
                                session = session,
                                cardColor = darkSurface,
                                accentColor = neonCyan,
                                onClick = { onItemClick(session.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryCard(
    session: Session,
    cardColor: Color,
    accentColor: Color,
    onClick: () -> Unit
) {
    val modeDisplay = when (session.mode) {
        "Focus", "Fokus"     -> stringResource(R.string.mode_focus)
        "Break", "Istirahat" -> stringResource(R.string.mode_break)
        else                 -> session.mode
    }
    val concentrationDisplay = when (session.concentration) {
        "Low", "Rendah"    -> stringResource(R.string.con_low)
        "Medium", "Sedang" -> stringResource(R.string.con_med)
        "High", "Tinggi"   -> stringResource(R.string.con_high)
        else               -> session.concentration
    }
    val categoryDisplay = when (session.category) {
        "Study", "Belajar"    -> stringResource(R.string.cat_study)
        "Assignment", "Tugas" -> stringResource(R.string.cat_assignment)
        "Others", "Lainnya"   -> stringResource(R.string.cat_others)
        else                  -> session.category
    }
    val dotColor = when (session.mode) {
        "Break", "Istirahat" -> Color(0xFFFF7EB3)
        else                 -> accentColor
    }

    ElevatedCard(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = cardColor),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(dotColor, RoundedCornerShape(4.dp))
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${stringResource(R.string.label_activity_name)}: ${session.courseName}",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${stringResource(R.string.label_select_mode)}: $modeDisplay",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = "${stringResource(R.string.label_concentration)}: $concentrationDisplay",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = "${stringResource(R.string.label_category)}: $categoryDisplay",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${session.duration} • ${session.timestamp}",
                    color = Color.Gray.copy(alpha = 0.6f),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun HistoryGridCard(
    session: Session,
    cardColor: Color,
    accentColor: Color,
    onClick: () -> Unit
) {
    val modeDisplay = when (session.mode) {
        "Focus", "Fokus"     -> stringResource(R.string.mode_focus)
        "Break", "Istirahat" -> stringResource(R.string.mode_break)
        else                 -> session.mode
    }
    val concentrationDisplay = when (session.concentration) {
        "Low", "Rendah"    -> stringResource(R.string.con_low)
        "Medium", "Sedang" -> stringResource(R.string.con_med)
        "High", "Tinggi"   -> stringResource(R.string.con_high)
        else               -> session.concentration
    }
    val categoryDisplay = when (session.category) {
        "Study", "Belajar"    -> stringResource(R.string.cat_study)
        "Assignment", "Tugas" -> stringResource(R.string.cat_assignment)
        "Others", "Lainnya"   -> stringResource(R.string.cat_others)
        else                  -> session.category
    }
    val dotColor = when (session.mode) {
        "Break", "Istirahat" -> Color(0xFFFF7EB3)
        else                 -> accentColor
    }

    ElevatedCard(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = cardColor),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(dotColor, RoundedCornerShape(4.dp))
            )
            Text(
                text = "${stringResource(R.string.label_activity_name)}: ${session.courseName}",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$modeDisplay • $concentrationDisplay",
                color = accentColor,
                fontSize = 11.sp,
                maxLines = 1
            )
            Text(
                text = categoryDisplay,
                color = Color.Gray,
                fontSize = 11.sp
            )
            Text(
                text = "${session.duration}\n${session.timestamp}",
                color = Color.Gray.copy(alpha = 0.6f),
                fontSize = 11.sp
            )
        }
    }
}