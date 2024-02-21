package io.github.japskiddin.androidfilepickercompose.filesystem.exceptions

import android.net.Uri

class DocumentFileNotFoundException(rootUri: Uri, path: String) :
    RuntimeException("Root uri: ${rootUri.path} and path $path")