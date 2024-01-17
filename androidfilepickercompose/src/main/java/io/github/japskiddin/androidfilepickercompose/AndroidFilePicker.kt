package io.github.japskiddin.androidfilepickercompose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.androidfilepickercompose.ui.components.ToolBar
import io.github.japskiddin.androidfilepickercompose.ui.theme.AndroidFilePickerComposeTheme

@Composable
fun AndroidFilePicker(
    modifier: Modifier = Modifier,
    pickerViewModel: PickerViewModel = viewModel()
) {
    AndroidFilePickerComposeTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val currentPickerFile by pickerViewModel.currentPickerFile.collectAsState()
            val toolbarTitle =
                currentPickerFile?.filename ?: stringResource(id = R.string.afp_app_name)

            Scaffold(
                topBar = ToolBar(
                    title = toolbarTitle,
                    onBackClick = { /*TODO*/ },
                    actions = {},
                    modifier = modifier.fillMaxWidth()
                ),
                content = { contentPadding ->
                    Box(
                        modifier = modifier
                            .padding(contentPadding)
                            .fillMaxSize()
                    ) {

                    }
                },
                modifier = modifier.fillMaxSize()
            )
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