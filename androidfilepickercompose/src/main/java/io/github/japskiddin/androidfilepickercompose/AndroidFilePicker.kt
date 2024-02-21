package io.github.japskiddin.androidfilepickercompose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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

@Composable
fun AndroidFilePicker(
    modifier: Modifier = Modifier,
    isDirectorySelect: Boolean = false,
    canCreateDirectory: Boolean = false,
    pickerViewModel: PickerViewModel = viewModel()
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
            AndroidFilePickerContent(
                currentPickerFile = currentPickerFile,
                isDirectorySelect = isDirectorySelect,
                canCreateDirectory = canCreateDirectory,
                storages = storages
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
    storages: List<StorageDirectory>
) {
    Scaffold(
        topBar = {
            ToolBar(
                title = currentPickerFile?.name ?: stringResource(id = R.string.afp_app_name),
                onBackClick = { /*TODO*/ },
                actions = {
                    if (canCreateDirectory) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.AddCircle,
                                contentDescription = stringResource(
                                    id = R.string.create_directory
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
                        StorageList(storages = storages)
                    } else {
                        FileList(files = mutableListOf())
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
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AndroidFilePickerPreview() {
    AndroidFilePickerComposeTheme(dynamicColor = false) {
        AndroidFilePickerContent(
            currentPickerFile = null,
            isDirectorySelect = true,
            canCreateDirectory = false,
            storages = emptyList()
        )
    }
}