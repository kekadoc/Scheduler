package app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import app.appColors

@Composable
fun TestUi() {
    val customColors = appColors(isDark = true)
    MaterialTheme(
        colors = customColors
    ) {
        Surface(

        ) {
            TestUiContent()
        }

    }
}

@Composable
private fun TestUiContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val styles = mapOf<String, TextStyle>(
             "h1" to MaterialTheme.typography.h1,
             "h2" to MaterialTheme.typography.h2,
             "h3" to MaterialTheme.typography.h3,
             "h4" to MaterialTheme.typography.h4,
             "h5" to MaterialTheme.typography.h5,
             "h6" to MaterialTheme.typography.h6,
             "subtitle1" to MaterialTheme.typography.subtitle1,
             "subtitle2" to MaterialTheme.typography.subtitle2,
             "body1" to MaterialTheme.typography.body1,
             "body2" to MaterialTheme.typography.body2,
             "button" to MaterialTheme.typography.button,
             "caption" to MaterialTheme.typography.caption,
             "overline" to MaterialTheme.typography.overline
        )
        styles.forEach { (text, textStyle) ->
            Text(
                text = text,
                style = textStyle
            )
        }
        Button(onClick = {}) {
            Text("Button")
        }
    }
}

fun Color.toRGB(): String {
    return "[${red * 255}, ${green * 255}, ${blue * 255}]"
}