package com.example.minivideojournalapp.feature.recording.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.minivideojournalapp.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    permission: List<String>,
    rationaleText: String,
    onAllGranted: () -> Unit
) {
    val permissionStates = rememberMultiplePermissionsState(permission)

    when {
        permissionStates.allPermissionsGranted -> {
            onAllGranted()
        }
        permissionStates.shouldShowRationale -> {
            AlertDialog(
                onDismissRequest = {},
                title = { Text(stringResource(R.string.permission_required)) },
                text = { Text(rationaleText) },
                confirmButton = {
                    TextButton(onClick = {
                        permissionStates.launchMultiplePermissionRequest()
                    }) {
                        Text(stringResource(R.string.grant_permission))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }
        else -> {
            LaunchedEffect(Unit) {
                permissionStates.launchMultiplePermissionRequest()
            }
        }
    }
}
