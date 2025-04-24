package com.example.minivideojournalapp.ui.shared

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

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
                title = { Text("Permissions Required") },
                text = { Text(rationaleText) },
                confirmButton = {
                    TextButton(onClick = {
                        permissionStates.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { }) {
                        Text("Cancel")
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
