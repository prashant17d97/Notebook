package app.debugdesk.notebook.ui.theme

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.platform.LocalContext


@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    contrast: Contrast,
    content: @Composable () -> Unit
) {
    
      val colorScheme = when {
          dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
              val context = LocalContext.current
              if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
          }
          
          darkTheme -> contrast.colorSchemeDark
          else -> contrast.colorSchemeLight
      }
    
      MaterialTheme(
        colorScheme = colorScheme,
        typography =  RobotoCondensed(),
        content = content
      )
}
    
    
 