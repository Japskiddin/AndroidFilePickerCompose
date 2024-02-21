package io.github.japskiddin.androidfilepickercompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.androidfilepickercompose.R
import io.github.japskiddin.androidfilepickercompose.data.model.PickerFile
import io.github.japskiddin.androidfilepickercompose.ui.theme.AndroidFilePickerComposeTheme

@Composable
fun FileList(
    files: List<PickerFile>,
    onFileClick: (PickerFile) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        items(items = files, key = { file -> file.path }) { file ->
            FileItem(
                file = file,
                onFileClick = onFileClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun FileItem(
    modifier: Modifier = Modifier,
    file: PickerFile,
    onFileClick: (PickerFile) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onFileClick(file) }
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        Image(
            painter = if (file.isDirectory) {
                painterResource(id = R.drawable.ic_afp_directory)
            } else {
                painterResource(id = R.drawable.ic_afp_file)
            },
            contentDescription = file.name,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
            modifier = modifier
                .size(36.dp)
                .align(Alignment.CenterVertically)
        )
        Column(modifier = modifier.padding(start = 16.dp)) {
            Text(
                text = file.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = if (file.isDirectory) {
                    stringResource(id = R.string.afp_directory)
                } else {
                    stringResource(id = R.string.afp_file)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = modifier.padding(top = 4.dp),
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
            "com/android/test.dat",
            "test.dat",
            false
        )
    )
    AndroidFilePickerComposeTheme {
        FileList(
            files = files,
            onFileClick = {},
        )
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
            ),
            onFileClick = {},
        )
    }
}