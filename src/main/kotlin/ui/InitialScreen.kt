package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import common.resources.R
import common.resources.api.LocalResourceQualifier
import common.resources.api.ResourceQualifier
import common.resources.api.stringResource
import java.util.*
import java.util.prefs.Preferences

@Composable
fun InitialScreen() {
    Column {
        OutlinedTextField(value = "PNIPU", onValueChange = {})

        CompositionLocalProvider(LocalResourceQualifier provides ResourceQualifier(Locale.ENGLISH)) {
            Text(stringResource(R.string.name))
        }
        CompositionLocalProvider(LocalResourceQualifier provides ResourceQualifier(Locale("ru", "ru"))) {
            Text(stringResource(R.string.name))
        }
    }
}