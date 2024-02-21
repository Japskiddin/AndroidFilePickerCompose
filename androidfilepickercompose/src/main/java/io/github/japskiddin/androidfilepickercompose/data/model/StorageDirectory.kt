package io.github.japskiddin.androidfilepickercompose.data.model

import androidx.annotation.DrawableRes

data class StorageDirectory(
    @JvmField
    val path: String,
    @JvmField
    val name: String,
    @JvmField
    @DrawableRes
    val iconRes: Int,
)