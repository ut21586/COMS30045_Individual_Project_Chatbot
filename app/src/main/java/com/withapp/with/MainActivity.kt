package com.withapp.with

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.services.HealthManager
import com.withapp.with.models.AppState
import com.withapp.with.ui.screens.MainContainer
import com.withapp.with.ui.theme.WithAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var healthManager: HealthManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        healthManager = HealthManager(this)

        // Register Health Connect permission result callback
        val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()
        val requestPermissions = registerForActivityResult(requestPermissionActivityContract) { granted ->
            if (granted.containsAll(healthManager.permissions)) {
                Toast.makeText(this, "健康数据授权成功！", Toast.LENGTH_SHORT).show()
            }
        }

        enableEdgeToEdge()
        setContent {
            WithAppTheme {
                val appState: AppState =       viewModel()

                // Call the MainContainer with required parameters
                MainContainer(
                    appState = appState,
                    onRequestPermission = {
                        // Launch the system permission dialog
                        requestPermissions.launch(healthManager.permissions)
                    }
                )
            }
        }
    }
}