package io.github.japskiddin.androidfilepickercompose

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.japskiddin.androidfilepickercompose.ui.theme.AndroidFilePickerComposeTheme

@Composable
fun AndroidFilePicker(
    modifier: Modifier = Modifier
) {
    AndroidFilePickerComposeTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

        }
    }
}

@Preview(
    name = "View Light mode",
    showBackground = true
)
@Preview(name = "View Dark mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AndroidFilePickerPreview() {
    AndroidFilePicker()
}