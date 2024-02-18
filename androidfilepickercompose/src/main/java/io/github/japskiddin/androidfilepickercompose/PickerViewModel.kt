package io.github.japskiddin.androidfilepickercompose

import android.content.Context
import androidx.lifecycle.ViewModel
import io.github.japskiddin.androidfilepickercompose.data.model.PickerFile
import io.github.japskiddin.androidfilepickercompose.filesystem.getStorageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PickerViewModel : ViewModel() {
    private val _currentPickerFile = MutableStateFlow<PickerFile?>(null)

    val currentPickerFile: StateFlow<PickerFile?> = _currentPickerFile

    fun initStorages(context: Context) {
        val list = getStorageData(context)
    }
}