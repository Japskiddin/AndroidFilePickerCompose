package io.github.japskiddin.androidfilepickercompose

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import io.github.japskiddin.androidfilepickercompose.data.model.PickerFile
import io.github.japskiddin.androidfilepickercompose.data.model.StorageDirectory
import io.github.japskiddin.androidfilepickercompose.filesystem.getStorageDirectoriesLegacy
import io.github.japskiddin.androidfilepickercompose.filesystem.getStorageDirectoriesNew
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PickerViewModel : ViewModel() {
    private val _currentPickerFile = MutableStateFlow<PickerFile?>(null)
    private val _storages = MutableStateFlow<MutableList<StorageDirectory>>(mutableListOf())

    val currentPickerFile: StateFlow<PickerFile?> = _currentPickerFile
    val storages: StateFlow<List<StorageDirectory>> = _storages

    fun initStorages(context: Context) {
        // TODO: завернуть в корутину?
        val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getStorageDirectoriesNew(context)
        } else {
            getStorageDirectoriesLegacy(context)
        }
        _storages.update {
            _storages.value.toMutableList().apply { this.addAll(list) }
        }
    }
}