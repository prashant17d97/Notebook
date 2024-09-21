package app.debugdesk.notebook.presentations.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import app.debugdesk.notebook.domain.model.AppAppearance
import app.debugdesk.notebook.domain.model.ColorContrast
import app.debugdesk.notebook.domain.model.isDakTheme


@Composable
actual fun AppTheme(
    appAppearance: AppAppearance,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        appAppearance.useSystemPalette && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
              val context = LocalContext.current
            if (appAppearance.themeMode.isDakTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                context
            )
          }

        appAppearance.themeMode.isDakTheme -> appAppearance.colorContrast.colorSchemeDark
        else -> appAppearance.colorContrast.colorSchemeLight
      }
    
      MaterialTheme(
        colorScheme = colorScheme,
        typography =  RobotoCondensed(),
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
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> colorContrast.colorSchemeDark
        else -> colorContrast.colorSchemeLight
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RobotoCondensed(),
        content = content
    )
}