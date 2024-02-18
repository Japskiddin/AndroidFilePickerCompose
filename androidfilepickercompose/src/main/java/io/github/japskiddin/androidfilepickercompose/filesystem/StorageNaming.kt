package io.github.japskiddin.androidfilepickercompose.filesystem

import android.content.Context
import androidx.annotation.IntDef
import io.github.japskiddin.androidfilepickercompose.R
import java.io.File


const val STORAGE_INTERNAL = 0
const val STORAGE_SD_CARD = 1
const val ROOT = 2
const val NOT_KNOWN = 3

@IntDef(STORAGE_INTERNAL, STORAGE_SD_CARD, ROOT, NOT_KNOWN)
annotation class DeviceDescription

/** Retrofit of [android.os.storage.StorageVolume.getDescription] to older apis  */
@DeviceDescription
fun getDeviceDescriptionLegacy(file: File): Int {
    val path = file.path
    return when (path) {
        "/storage/emulated/legacy", "/storage/emulated/0", "/mnt/sdcard" -> STORAGE_INTERNAL
        "/storage/sdcard", "/storage/sdcard1" -> STORAGE_SD_CARD
        "/" -> ROOT
        else -> NOT_KNOWN
    }
}

fun getNameForDeviceDescription(
    context: Context,
    file: File,
    @DeviceDescription deviceDescription: Int
): String {
    return when (deviceDescription) {
        STORAGE_INTERNAL -> context.getString(R.string.storage_internal)
        STORAGE_SD_CARD -> context.getString(R.string.storage_sd_card)
        ROOT -> context.getString(R.string.root_directory)
        NOT_KNOWN -> file.name
        else -> file.name
    }
}