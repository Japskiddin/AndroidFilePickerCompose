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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.japskiddin.androidfilepickercompose.R
import io.github.japskiddin.androidfilepickercompose.data.model.StorageDirectory
import io.github.japskiddin.androidfilepickercompose.ui.theme.AndroidFilePickerComposeTheme

@Composable
fun StorageList(
    storages: List<StorageDirectory>,
    onStorageClick: (StorageDirectory) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        items(items = storages, key = { storage -> storage.path }) { storage ->
            StorageItem(
                storage = storage,
                onStorageClick = onStorageClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun StorageItem(
    modifier: Modifier = Modifier,
    storage: StorageDirectory,
    onStorageClick: (StorageDirectory) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onStorageClick(storage) }
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        Image(
            painter = painterResource(id = storage.iconRes),
            contentDescription = storage.name,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
            modifier = modifier
                .size(36.dp)
                .align(Alignment.CenterVertically)
        )
        Column(modifier = modifier.padding(start = 16.dp)) {
            Text(
                text = storage.path,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = storage.name,
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
        StorageList(
            storages = storages,
            onStorageClick = {}
        )
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
            ),
            onStorageClick = {},
        )
    }
}