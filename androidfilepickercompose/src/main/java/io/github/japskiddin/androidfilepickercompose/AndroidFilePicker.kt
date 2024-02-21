package io.github.japskiddin.androidfilepickercompose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.androidfilepickercompose.data.model.PickerFile
import io.github.japskiddin.androidfilepickercompose.data.model.StorageDirectory
import io.github.japskiddin.androidfilepickercompose.ui.components.FileList
import io.github.japskiddin.androidfilepickercompose.ui.components.StorageList
import io.github.japskiddin.androidfilepickercompose.ui.components.ToolBar
import io.github.japskiddin.androidfilepickercompose.ui.theme.AndroidFilePickerComposeTheme
import java.io.File

@Composable
fun AndroidFilePicker(
    modifier: Modifier = Modifier,
    isDirectorySelect: Boolean = false,
    canCreateDirectory: Boolean = false,
    pickerViewModel: PickerViewModel = viewModel(),
) {
    val context = LocalContext.current
    pickerViewModel.initStorages(context)

    AndroidFilePickerComposeTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val currentPickerFile by pickerViewModel.currentPickerFile.collectAsState()
            val storages by pickerViewModel.storages.collectAsState()
            val isBackAvailable by pickerViewModel.isBackAvailable.collectAsState()
            val toolbarTitle by pickerViewModel.toolbarTitle.collectAsState()
            AndroidFilePickerContent(
                currentPickerFile = currentPickerFile,
                isDirectorySelect = isDirectorySelect,
                canCreateDirectory = canCreateDirectory,
                storages = storages,
                isBackAvailable = isBackAvailable,
                toolbarTitle = toolbarTitle,
                onStorageClick = { storage ->
                    pickerViewModel.selectStorage(storage)
                },
                onFileClick = { pickerFile ->
                    pickerViewModel.selectFile(pickerFile)
                },
                onBackPressed = {
                    pickerViewModel.navigateUp()
                }
            )
        }
    }
}

@Composable
fun AndroidFilePickerContent(
    modifier: Modifier = Modifier,
    isDirectorySelect: Boolean,
    canCreateDirectory: Boolean,
    currentPickerFile: PickerFile?,
    storages: List<StorageDirectory>,
    isBackAvailable: Boolean,
    toolbarTitle: String,
    onStorageClick: (StorageDirectory) -> Unit,
    onFileClick: (PickerFile) -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = toolbarTitle.ifEmpty { stringResource(id = R.string.afp_select_storage) },
                navigationIcon = {
                    if (isBackAvailable) {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = stringResource(
                                    id = R.string.afp_back
                                )
                            )
                        }
                    }
                },
                actions = {
                    if (canCreateDirectory) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.AddCircle,
                                contentDescription = stringResource(
                                    id = R.string.afp_create_directory
                                )
                            )
                        }
                    }
                },
                modifier = modifier.fillMaxWidth()
            )
        },
        content = { contentPadding ->
            Box(
                modifier = modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {
                Column(modifier = modifier.fillMaxSize()) {
                    if (isDirectorySelect) {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = modifier.align(alignment = Alignment.CenterHorizontally)
                        ) {
                            Text(text = stringResource(id = R.string.afp_select))
                        }
                    }
                    if (currentPickerFile == null) {
                        StorageList(
                            storages = storages,
                            onStorageClick = onStorageClick,
                        )
                    } else {
                        val files: MutableList<PickerFile> = mutableListOf()
                        currentPickerFile.path.let { filepath ->
                            File(filepath).listFiles().let { array ->
                                for (file in array) {
                                    files.add(PickerFile(file.path, file.name, file.isDirectory))
                                }
                            }
                        }
                        FileList(
                            files = files,
                            onFileClick = onFileClick,
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Preview(
    name = "View Light mode",
    showBackground = true
)
@Preview(
    name = "View Dark mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun AndroidFilePickerPreview() {
    val storages = listOf(
        StorageDirectory(
            path = "storage/emulated/0",
            name = "Внутренняя память",
            iconRes = R.drawable.ic_drawer_root
        ),
        StorageDirectory(
            path = "sdcard1",
            name = "SD Card",
            iconRes = R.drawable.ic_sd_storage
        )
    )
    AndroidFilePickerComposeTheme(dynamicColor = false) {
        AndroidFilePickerContent(
            currentPickerFile = null,
            isDirectorySelect = true,
            canCreateDirectory = false,
            storages = storages,
            isBackAvailable = false,
            toolbarTitle = "Title",
            onStorageClick = {},
            onFileClick = {},
            onBackPressed = {},
        )
    }
}