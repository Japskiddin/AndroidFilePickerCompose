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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.androidfilepickercompose.R
import io.github.japskiddin.androidfilepickercompose.data.model.StorageDirectory
import io.github.japskiddin.androidfilepickercompose.ui.theme.AndroidFilePickerComposeTheme

@Composable
fun StorageList(storages: List<StorageDirectory>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = storages, key = { storage -> storage.path }) { storage ->
            StorageItem(storage = storage, modifier = modifier)
        }
    }
}

@Composable
fun StorageItem(modifier: Modifier = Modifier, storage: StorageDirectory) {
    Row(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = storage.iconRes),
            contentDescription = storage.name,
            modifier = modifier
                .size(36.dp)
                .align(Alignment.CenterVertically)
        )
        Column(modifier = modifier.padding(start = 16.dp)) {
            Text(text = storage.path)
            Text(text = storage.name)
        }
    }
}

@Preview(
    name = "List",
    showBackground = true
)
@Composable
fun StorageListPreview() {
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
    AndroidFilePickerComposeTheme {
        StorageList(storages = storages)
    }
}

@Preview(
    name = "Item",
    showBackground = true
)
@Composable
fun StorageItemPreview() {
    AndroidFilePickerComposeTheme {
        StorageItem(
            storage = StorageDirectory(
                path = "storage/emulated/0",
                name = "Внутренняя память",
                iconRes = R.drawable.ic_drawer_root
            )
        )
    }
}