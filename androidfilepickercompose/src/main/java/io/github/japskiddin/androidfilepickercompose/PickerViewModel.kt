package io.github.japskiddin.androidfilepickercompose

import androidx.lifecycle.ViewModel
import io.github.japskiddin.androidfilepickercompose.data.model.PickerFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PickerViewModel : ViewModel() {
    private val _currentPickerFile = MutableStateFlow<PickerFile?>(null)

    val currentPickerFile: StateFlow<PickerFile?> = _currentPickerFile
}