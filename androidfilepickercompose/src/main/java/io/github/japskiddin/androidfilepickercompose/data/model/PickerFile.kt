package io.github.japskiddin.androidfilepickercompose.data.model

data class PickerFile(
    @JvmField
    val path: String,
    @JvmField
    val name: String,
    @JvmField
    val isDirectory: Boolean
) : Comparable<PickerFile> {
    override fun compareTo(other: PickerFile): Int {
        return path.compareTo(other.path)
    }

    override fun toString(): String {
        return "$path isDirectory=$isDirectory"
    }
}