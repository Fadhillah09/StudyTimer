@file:OptIn(ExperimentalMaterial3Api::class)

package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Study Timer")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = onStartClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Start Timer")
            }

            Button(
                onClick = onHistoryClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("History")
            }

            Button(
                onClick = onSettingsClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Settings")
            }
        }
    }
}