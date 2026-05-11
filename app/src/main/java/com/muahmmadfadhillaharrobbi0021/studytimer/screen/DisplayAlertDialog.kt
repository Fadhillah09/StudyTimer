package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muahmmadfadhillaharrobbi0021.studytimer.R

@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        text = { Text(text = stringResource(R.string.dialog_delete_message)) },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(R.string.dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(R.string.dialog_cancel))
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}