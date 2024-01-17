package io.github.japskiddin.androidfilepickecompose.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.github.japskiddin.androidfilepickercompose.AndroidFilePicker

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // TODO: 29.05.2022 поучиться написанию тестов

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidFilePicker()
        }
    }
}