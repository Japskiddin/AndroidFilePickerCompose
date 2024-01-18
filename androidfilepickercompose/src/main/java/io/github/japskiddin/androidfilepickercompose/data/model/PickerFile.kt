package io.github.japskiddin.androidfilepickercompose.data.model

data class PickerFile(
    @JvmField
    val filepath: String,
    @JvmField
    val filename: String,
    @JvmField
    val isDirectory: Boolean
) : Comparable<PickerFile> {
    override fun compareTo(other: PickerFile): Int {
        return filepath.compareTo(other.filepath)
    }

    override fun toString(): String {
        return "$filepath isDirectory=$isDirectory"
    }
}