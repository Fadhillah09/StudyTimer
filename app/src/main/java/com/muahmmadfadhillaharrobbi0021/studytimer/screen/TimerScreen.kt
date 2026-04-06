@file:OptIn(ExperimentalMaterial3Api::class)

package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muahmmadfadhillaharrobbi0021.studytimer.R
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(
    onBackClick: () -> Unit,
    activityName: String,
    durationMinutes: Int,
    category: String,
    concentration: Int,
    mode: String
) {
    val context = LocalContext.current
    var timeLeft by remember {
        mutableIntStateOf(if (durationMinutes == 10) 10 else durationMinutes * 60)
    }
    var isRunning by remember { mutableStateOf(false) }
    var hasShared by remember { mutableStateOf(false) }

    val neonCyan = Color(0xFF00E5FF)
    val darkBackground = Color(0xFF0A0A0A)
    val darkSurface = Color(0xFF161616)
    val textPrimary = Color.White

    val darkGradient = Brush.verticalGradient(
        colors = listOf(darkBackground, Color(0xFF001020))
    )

    val breakRecommendation = remember(durationMinutes, concentration) {
        when {
            durationMinutes == 10 -> ""
            durationMinutes >= 90 -> context.getString(R.string.rec_heavy)
            durationMinutes >= 50 -> context.getString(R.string.rec_long)
            concentration == 1 -> context.getString(R.string.rec_low_con)
            else -> context.getString(R.string.rec_default)
        }
    }

    LaunchedEffect(isRunning, timeLeft) {
        if (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        } else if (timeLeft == 0 && isRunning && !hasShared) {
            isRunning = false
            hasShared = true

            val sharedPref = context.getSharedPreferences("study_timer_prefs", Context.MODE_PRIVATE)
            val concentrationText = when(concentration) {
                1 -> "Low"
                2 -> "Med"
                3 -> "High"
                else -> "Med"
            }
            val isAlarmEnabled = sharedPref.getBoolean("notification_enabled", false)

            if (isAlarmEnabled) {
                playTestSound(context)
            }
            val currentHistory = sharedPref.getString("history_data", "")
            val timeLabel = if (durationMinutes == 10) "10 sec" else "$durationMinutes min"
            val newEntry = " [$mode] [$concentrationText] $category: $activityName ($timeLabel)"
            val updatedHistory = if (currentHistory.isNullOrEmpty()) newEntry else "$currentHistory|$newEntry"
            sharedPref.edit().putString("history_data", updatedHistory).apply()
            shareResults(context, activityName, durationMinutes)
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.title_home),
                        fontWeight = FontWeight.ExtraBold,
                        color = textPrimary
                    )
                },
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$category - $activityName",
                    color = neonCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = breakRecommendation,
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                ElevatedCard(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(300.dp)
                        .padding(8.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = darkSurface),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(240.dp)
                                .clip(CircleShape)
                                .background(neonCyan.copy(alpha = 0.05f))
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = String.format("%02d:%02d", minutes, seconds),
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = neonCyan,
                                fontSize = 72.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedIconButton(
                        onClick = {
                            timeLeft = durationMinutes * 60
                            isRunning = false
                            hasShared = false
                        },
                        modifier = Modifier.size(60.dp),
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Gray)
                    }

                    Spacer(modifier = Modifier.width(32.dp))

                    Button(
                        onClick = { isRunning = !isRunning },
                        modifier = Modifier.size(90.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neonCyan,
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
                    ) {
                        Icon(
                            imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(32.dp))

                    OutlinedIconButton(
                        onClick = { shareResults(context, activityName, durationMinutes) },
                        modifier = Modifier.size(60.dp),
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(1.dp, neonCyan)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = neonCyan)
                    }
                }
            }
        }
    }
}

fun shareResults(context: Context, activity: String, minutes: Int) {
    val message = context.getString(R.string.share_message, activity, minutes)
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Share your progress!")
    context.startActivity(shareIntent)
}