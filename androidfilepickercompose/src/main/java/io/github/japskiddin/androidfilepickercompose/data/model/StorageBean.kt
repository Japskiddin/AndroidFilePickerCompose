package io.github.japskiddin.androidfilepickercompose.data.model

data class StorageBean(
    val path: String?,
    val mounted: String?,
    val removable: Boolean,
    val totalSize: Long,
    val availableSize: Long
)