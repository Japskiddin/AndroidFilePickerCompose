package io.github.japskiddin.androidfilepickercompose

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.japskiddin.androidfilepickercompose.data.model.PickerFile
import io.github.japskiddin.androidfilepickercompose.data.model.StorageDirectory
import io.github.japskiddin.androidfilepickercompose.filesystem.getStorageDirectoriesLegacy
import io.github.japskiddin.androidfilepickercompose.filesystem.getStorageDirectoriesNew
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class PickerViewModel : ViewModel() {
    private val _currentPickerFile = MutableStateFlow<PickerFile?>(null)
    private val _storages = MutableStateFlow<MutableList<StorageDirectory>>(mutableListOf())
    private val _isBackAvailable = MutableStateFlow(false)
    private val _toolbarTitle = MutableStateFlow("")

    val currentPickerFile: StateFlow<PickerFile?> = _currentPickerFile
    val storages: StateFlow<List<StorageDirectory>> = _storages
    val isBackAvailable: StateFlow<Boolean> = _isBackAvailable
    val toolbarTitle: StateFlow<String> = _toolbarTitle

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

    fun selectStorage(storage: StorageDirectory) {
        viewModelScope.launch(Dispatchers.IO) {
            val isDirectory = File(storage.path).isDirectory
            _currentPickerFile.update {
                PickerFile(storage.path, storage.name, isDirectory)
            }
        }
    }

    fun selectFile(pickerFile: PickerFile) {
        _currentPickerFile.update { pickerFile }
    }

    fun navigateUp() {

    }
}