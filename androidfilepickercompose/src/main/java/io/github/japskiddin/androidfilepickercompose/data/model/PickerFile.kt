package io.github.japskiddin.androidfilepickercompose.data.model

data class PickerFile(
    val filepath: String,
    val filename: String,
    val isDirectory: Boolean,
    val isFile: Boolean
) : Comparable<PickerFile> {
    override fun compareTo(other: PickerFile): Int {
        return filepath.compareTo(other.filepath)
    }

    override fun toString(): String {
        return "$filepath directory=$isDirectory file=$isFile"
    }
}