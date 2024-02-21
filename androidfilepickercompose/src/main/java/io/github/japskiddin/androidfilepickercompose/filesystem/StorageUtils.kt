package io.github.japskiddin.androidfilepickercompose.filesystem

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.N
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.text.TextUtils
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.getSystemService
import io.github.japskiddin.androidfilepickercompose.BuildConfig
import io.github.japskiddin.androidfilepickercompose.R
import io.github.japskiddin.androidfilepickercompose.data.model.StorageDirectory
import io.github.japskiddin.androidfilepickercompose.filesystem.usb.SingletonUsbOtg
import io.github.japskiddin.androidfilepickercompose.utils.OTGUtil
import io.github.japskiddin.androidfilepickercompose.utils.checkStoragePermission
import java.io.File
import java.io.IOException
import java.lang.reflect.Field
import java.util.Collections
import java.util.Locale
import java.util.regex.Pattern


private const val TAG = "StorageUtils"
private const val A_GB: Long = 1073741824
private const val A_MB: Long = 1048576
private const val A_KB = 1024

private const val DEFAULT_FALLBACK_STORAGE_PATH = "/storage/sdcard0"
private const val INTERNAL_SHARED_STORAGE = "Internal shared storage"
private val DIR_SEPARATOR: Pattern = Pattern.compile("/")

/**
 * @return All available storage volumes (including internal storage, SD-Cards and USB devices)
 */
@TargetApi(N)
@Synchronized
fun getStorageDirectoriesNew(context: Context): ArrayList<StorageDirectory> {
    // Final set of paths
    val volumes: ArrayList<StorageDirectory> = ArrayList()
    val storageManager: StorageManager =
        getSystemService(context, StorageManager::class.java) ?: return volumes
    for (volume in storageManager.storageVolumes) {
        if (!volume.state.equals(Environment.MEDIA_MOUNTED, ignoreCase = true)
            && !volume.state.equals(Environment.MEDIA_MOUNTED_READ_ONLY, ignoreCase = true)
        ) {
            continue
        }
        val path: File = getVolumeDirectory(volume)
        var name = volume.getDescription(context)
        if (INTERNAL_SHARED_STORAGE == name) {
            name = context.getString(R.string.storage_internal)
        }
        @DrawableRes val icon: Int = if (!volume.isRemovable) {
            R.drawable.ic_phone_android
        } else {
            // HACK: There is no reliable way to distinguish USB and SD external storage
            // However it is often enough to check for "USB" String
            if (name.uppercase(Locale.getDefault())
                    .contains("USB") || path.path.uppercase(Locale.getDefault()).contains("USB")
            ) {
                R.drawable.ic_usb
            } else {
                R.drawable.ic_sd_storage
            }
        }
        volumes.add(StorageDirectory(path.path, name, icon))
    }
    return volumes
}

/**
 * Returns all available SD-Cards in the system (include emulated)
 *
 * <p>Warning: Hack! Based on Android source code of version 4.3 (API 18) Because there was no
 * standard way to get it before android N
 *
 * @return All available SD-Cards in the system (include emulated)
 */
@Synchronized
fun getStorageDirectoriesLegacy(context: Context): ArrayList<StorageDirectory> {
    val rv: MutableList<String> = ArrayList()

    // Primary physical SD-CARD (not emulated)
    val rawExternalStorage = System.getenv("EXTERNAL_STORAGE") ?: ""
    // All Secondary SD-CARDs (all exclude primary) separated by ":"
    val rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE") ?: ""
    // Primary emulated SD-CARD
    val rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET") ?: ""
    if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
        // Device has physical external storage; use plain paths.
        if (rawExternalStorage.isEmpty()) {
            // EXTERNAL_STORAGE undefined; falling back to default.
            // Check for actual existence of the directory before adding to list
            if (File(DEFAULT_FALLBACK_STORAGE_PATH).exists()) {
                rv.add(DEFAULT_FALLBACK_STORAGE_PATH)
            } else {
                // We know nothing else, use Environment's fallback
                rv.add(Environment.getExternalStorageDirectory().absolutePath)
            }
        } else {
            rv.add(rawExternalStorage)
        }
    } else {
        // Device has emulated storage; external storage paths should have
        // userId burned into them.
        val rawUserId: String
        val path = Environment.getExternalStorageDirectory().absolutePath
        val folders: Array<String> = DIR_SEPARATOR.split(path)
        val lastFolder = folders[folders.size - 1]
        var isDigit = false
        try {
            lastFolder.toInt()
            isDigit = true
        } catch (ignored: NumberFormatException) {
        }
        rawUserId = if (isDigit) lastFolder else ""
        // /storage/emulated/0[1,2,...]
        if (TextUtils.isEmpty(rawUserId)) {
            rv.add(rawEmulatedStorageTarget)
        } else {
            rv.add(rawEmulatedStorageTarget + File.separator + rawUserId)
        }
    }
    // Add all secondary storages
    if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
        // All Secondary SD-CARDs splited into array
        val rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator.toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()
        Collections.addAll(rv, *rawSecondaryStorages)
    }
    if (SDK_INT >= M && checkStoragePermission(context)) rv.clear()
    val strings: Array<String> = getExtSdCardPathsForActivity(context)
    for (s in strings) {
        val f = File(s)
        if (!rv.contains(s) && canListFiles(f)) rv.add(s)
    }
    val usb = getUsbDrive()
    if (usb != null && !rv.contains(usb.path)) {
        rv.add(usb.path)
    }
    if (SingletonUsbOtg.getInstance().isDeviceConnected()) {
        rv.add(OTGUtil.PREFIX_OTG + "/")
    }

    // Assign a label and icon to each directory
    val volumes: ArrayList<StorageDirectory> = ArrayList()
    for (file in rv) {
        val f = File(file)
        @DrawableRes val icon: Int = when (file) {
            "/storage/emulated/legacy", "/storage/emulated/0", "/mnt/sdcard" -> {
                R.drawable.ic_phone_android
            }

            "/storage/sdcard1" -> {
                R.drawable.ic_sd_storage
            }

            "/" -> {
                R.drawable.ic_drawer_root
            }

            else -> {
                R.drawable.ic_sd_storage
            }
        }
        @DeviceDescription val deviceDescription: Int = getDeviceDescriptionLegacy(f)
        val name: String = getNameForDeviceDescription(context, f, deviceDescription)
        volumes.add(StorageDirectory(file, name, icon))
    }
    return volumes
}

fun getUsbDrive(): File? {
    var parent = File("/storage")
    try {
        for (f in parent.listFiles()) if (f.exists() && f.name.lowercase(Locale.getDefault())
                .contains("usb") && f.canExecute()
        ) return f
    } catch (ignored: Exception) {
    }
    parent = File("/mnt/sdcard/usbStorage")
    if (parent.exists() && parent.canExecute()) return parent
    parent = File("/mnt/sdcard/usb_storage")
    return if (parent.exists() && parent.canExecute()) parent else null
}

fun getExtSdCardPathsForActivity(context: Context): Array<String> {
    val paths: MutableList<String> = ArrayList()
    for (file in context.getExternalFilesDirs("external")) {
        if (file != null) {
            val index = file.absolutePath.lastIndexOf("/Android/data")
            if (index < 0) {
                if (BuildConfig.DEBUG) {
                    Log.w(TAG, "Unexpected external file dir: $file.absolutePath")
                }
            } else {
                var path = file.absolutePath.substring(0, index)
                try {
                    path = File(path).canonicalPath
                } catch (e: IOException) {
                    // Keep non-canonical path.
                }
                paths.add(path)
            }
        }
    }
    if (paths.isEmpty()) paths.add("/storage/sdcard1")
    return paths.toTypedArray()
}

fun canListFiles(f: File): Boolean {
    return f.canRead() && f.isDirectory
}

@SuppressLint("PrivateApi")
@TargetApi(N)
fun getVolumeDirectory(volume: StorageVolume?): File {
    return try {
        val f: Field = StorageVolume::class.java.getDeclaredField("mPath")
        f.isAccessible = true
        f.get(volume) as File
    } catch (e: java.lang.Exception) {
        // This shouldn't fail, as mPath has been there in every version
        throw RuntimeException(e)
    }
}