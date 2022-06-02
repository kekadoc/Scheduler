package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import common.resources.R
import common.resources.api.LocalResourceQualifier
import common.resources.api.ResourceQualifier
import common.resources.api.stringResource
import common.view_model.ViewModel
import common.view_model.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.*

// TODO: 02.06.2022
class InitialScreenViewModel : ViewModel() {
    val value = flow<Int> {
        var a = 0
        while (true) {
            emit(a++)
            delay(1_000)
        }
    }
}

@Composable
fun InitialScreen(viewModel: InitialScreenViewModel = viewModel()) {
    val counter = viewModel.value.collectAsState(0)

    Column {
        OutlinedTextField(value = "PNIPU", onValueChange = {})
        Text(text = counter.value.toString())
        CompositionLocalProvider(LocalResourceQualifier provides ResourceQualifier(Locale.ENGLISH)) {
            Text(stringResource(R.string.name))
        }
        CompositionLocalProvider(LocalResourceQualifier provides ResourceQualifier(Locale("ru", "ru"))) {
            Text(stringResource(R.string.name))
        }
    }
}