package io.github.japskiddin.androidfilepickercompose.utils

import android.content.Context
import io.github.japskiddin.androidfilepickercompose.R
import java.io.File

object StorageNamingHelper {
    fun getNameForDeviceDescription(
        context: Context,
        file: File,
        @StorageNaming.DeviceDescription deviceDescription: Int
    ): String {
        return when (deviceDescription) {
            StorageNaming.STORAGE_INTERNAL -> context.getString(R.string.storage_internal)
            StorageNaming.STORAGE_SD_CARD -> context.getString(R.string.storage_sd_card)
            StorageNaming.ROOT -> context.getString(R.string.root_directory)
            StorageNaming.NOT_KNOWN -> file.name
            else -> file.name
        }
    }
}

