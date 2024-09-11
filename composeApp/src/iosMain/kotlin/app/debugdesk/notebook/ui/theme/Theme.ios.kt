package app.debugdesk.notebook.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    contrast: Contrast,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> contrast.colorSchemeLight
        else -> contrast.colorSchemeDark
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RobotoCondensed(),
        content = content
    )
}