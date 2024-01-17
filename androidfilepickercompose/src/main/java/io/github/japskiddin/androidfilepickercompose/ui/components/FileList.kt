package io.github.japskiddin.androidfilepickercompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.androidfilepickercompose.R
import io.github.japskiddin.androidfilepickercompose.data.model.PickerFile
import io.github.japskiddin.androidfilepickercompose.ui.theme.AndroidFilePickerComposeTheme

@Composable
fun FileList(files: List<PickerFile>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = files, key = { file -> file.filepath }) { file ->
            FileItem(file = file, modifier = modifier)
        }
    }
}

@Composable
fun FileItem(modifier: Modifier = Modifier, file: PickerFile) {
    Row(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = if (file.isDirectory) {
                painterResource(id = R.drawable.ic_afp_directory)
            } else {
                painterResource(id = R.drawable.ic_afp_file)
            },
            contentDescription = file.filename,
            modifier = modifier.size(36.dp)
        )
        Column(modifier = modifier.padding(start = 16.dp)) {
            Text(text = file.filename)
            Text(
                text = if (file.isDirectory) {
                    stringResource(id = R.string.directory)
                } else {
                    stringResource(id = R.string.file)
                }
            )
        }
    }
}

@Preview(
    name = "List",
    showBackground = true
)
@Composable
fun FileListPreview() {
    val files = listOf(
        PickerFile(
            "com/android/test.txt",
            "test.txt",
            false
        ),
        PickerFile(
            "ru/test/another.png",
            "/another.png",
            false
        ),
        PickerFile(
            "com/android/folder",
            "folder",
            true
        ),
        PickerFile(
            "com/android/test.txt",
            "test.txt",
            false
        )
    )
    AndroidFilePickerComposeTheme {
        FileList(files = files)
    }
}

@Preview(
    name = "Item",
    showBackground = true
)
@Composable
fun FileItemPreview() {
    AndroidFilePickerComposeTheme {
        FileItem(
            file = PickerFile(
                "com/android/test.txt",
                "test.txt",
                false
            )
        )
    }
}