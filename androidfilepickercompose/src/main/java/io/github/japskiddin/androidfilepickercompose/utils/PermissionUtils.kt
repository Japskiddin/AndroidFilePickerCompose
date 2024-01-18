package io.github.japskiddin.androidfilepickercompose.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat

fun checkStoragePermission(context: Context): Boolean {
    // Verify that all required contact permissions have been granted.
    return if (SDK_INT >= Build.VERSION_CODES.R) {
        ActivityCompat.checkSelfPermission(
            context,
            Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            context,
            Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED || Environment.isExternalStorageManager()
    } else {
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}