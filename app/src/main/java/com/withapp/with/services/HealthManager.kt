package com.withapp.with.services

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.BloodGlucoseRecord

class HealthManager(private val context: Context) {

    // Define permissions
    val permissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(BloodGlucoseRecord::class)
    )

    // Using lazy initialization to prevent crashes on devices without Health Connect
    private val healthConnectClient by lazy {
        try { HealthConnectClient.getOrCreate(context) } catch (e: Exception) { null }
    }

    suspend fun hasAllPermissions(): Boolean {
        return healthConnectClient?.permissionController?.getGrantedPermissions()?.containsAll(permissions) ?: false
    }

    // Mock values for the UI to display in the meantime
    var latestGlucose: Double = 5.6
    var latestSteps: Long = 8600
}