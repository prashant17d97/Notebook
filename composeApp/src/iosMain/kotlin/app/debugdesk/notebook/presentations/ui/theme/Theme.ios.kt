package app.debugdesk.notebook.presentations.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import app.debugdesk.notebook.domain.model.AppAppearance
import app.debugdesk.notebook.domain.model.ColorContrast
import app.debugdesk.notebook.domain.model.isDakTheme

@Composable
actual fun AppTheme(
    appAppearance: AppAppearance,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        appAppearance.themeMode.isDakTheme -> appAppearance.colorContrast.colorSchemeDark
        else -> appAppearance.colorContrast.colorSchemeLight
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RobotoCondensed(),
        content = content
    )
}

@Composable
actual fun AppThemePreview(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    colorContrast: ColorContrast,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> colorContrast.colorSchemeLight
        else -> colorContrast.colorSchemeDark
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RobotoCondensed(),
        content = content
    )
}