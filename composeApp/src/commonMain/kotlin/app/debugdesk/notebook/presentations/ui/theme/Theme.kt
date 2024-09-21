package app.debugdesk.notebook.presentations.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import app.debugdesk.notebook.domain.model.AppAppearance
import app.debugdesk.notebook.domain.model.ColorContrast
import app.debugdesk.notebook.presentations.ui.colors.backgroundDark
import app.debugdesk.notebook.presentations.ui.colors.backgroundDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.backgroundDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.backgroundLight
import app.debugdesk.notebook.presentations.ui.colors.backgroundLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.backgroundLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.errorContainerDark
import app.debugdesk.notebook.presentations.ui.colors.errorContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.errorContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.errorContainerLight
import app.debugdesk.notebook.presentations.ui.colors.errorContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.errorContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.errorDark
import app.debugdesk.notebook.presentations.ui.colors.errorDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.errorDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.errorLight
import app.debugdesk.notebook.presentations.ui.colors.errorLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.errorLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.inverseOnSurfaceDark
import app.debugdesk.notebook.presentations.ui.colors.inverseOnSurfaceDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.inverseOnSurfaceDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.inverseOnSurfaceLight
import app.debugdesk.notebook.presentations.ui.colors.inverseOnSurfaceLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.inverseOnSurfaceLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.inversePrimaryDark
import app.debugdesk.notebook.presentations.ui.colors.inversePrimaryDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.inversePrimaryDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.inversePrimaryLight
import app.debugdesk.notebook.presentations.ui.colors.inversePrimaryLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.inversePrimaryLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.inverseSurfaceDark
import app.debugdesk.notebook.presentations.ui.colors.inverseSurfaceDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.inverseSurfaceDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.inverseSurfaceLight
import app.debugdesk.notebook.presentations.ui.colors.inverseSurfaceLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.inverseSurfaceLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onBackgroundDark
import app.debugdesk.notebook.presentations.ui.colors.onBackgroundDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onBackgroundDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onBackgroundLight
import app.debugdesk.notebook.presentations.ui.colors.onBackgroundLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onBackgroundLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onErrorContainerDark
import app.debugdesk.notebook.presentations.ui.colors.onErrorContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onErrorContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onErrorContainerLight
import app.debugdesk.notebook.presentations.ui.colors.onErrorContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onErrorContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onErrorDark
import app.debugdesk.notebook.presentations.ui.colors.onErrorDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onErrorDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onErrorLight
import app.debugdesk.notebook.presentations.ui.colors.onErrorLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onErrorLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryContainerDark
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryContainerLight
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryDark
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryLight
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onPrimaryLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryContainerDark
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryContainerLight
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryDark
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryLight
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onSecondaryLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceDark
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceLight
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceVariantDark
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceVariantDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceVariantDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceVariantLight
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceVariantLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onSurfaceVariantLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryContainerDark
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryContainerLight
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryDark
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryLight
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.onTertiaryLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.outlineDark
import app.debugdesk.notebook.presentations.ui.colors.outlineDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.outlineDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.outlineLight
import app.debugdesk.notebook.presentations.ui.colors.outlineLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.outlineLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.outlineVariantDark
import app.debugdesk.notebook.presentations.ui.colors.outlineVariantDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.outlineVariantDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.outlineVariantLight
import app.debugdesk.notebook.presentations.ui.colors.outlineVariantLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.outlineVariantLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.primaryContainerDark
import app.debugdesk.notebook.presentations.ui.colors.primaryContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.primaryContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.primaryContainerLight
import app.debugdesk.notebook.presentations.ui.colors.primaryContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.primaryContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.primaryDark
import app.debugdesk.notebook.presentations.ui.colors.primaryDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.primaryDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.primaryLight
import app.debugdesk.notebook.presentations.ui.colors.primaryLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.primaryLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.scrimDark
import app.debugdesk.notebook.presentations.ui.colors.scrimDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.scrimDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.scrimLight
import app.debugdesk.notebook.presentations.ui.colors.scrimLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.scrimLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.secondaryContainerDark
import app.debugdesk.notebook.presentations.ui.colors.secondaryContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.secondaryContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.secondaryContainerLight
import app.debugdesk.notebook.presentations.ui.colors.secondaryContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.secondaryContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.secondaryDark
import app.debugdesk.notebook.presentations.ui.colors.secondaryDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.secondaryDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.secondaryLight
import app.debugdesk.notebook.presentations.ui.colors.secondaryLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.secondaryLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceBrightDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceBrightDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceBrightDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceBrightLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceBrightLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceBrightLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighestDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighestDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighestDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighestLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighestLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerHighestLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowestDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowestDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowestDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowestLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowestLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceContainerLowestLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceDimDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceDimDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceDimDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceDimLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceDimLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceDimLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceVariantDark
import app.debugdesk.notebook.presentations.ui.colors.surfaceVariantDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceVariantDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceVariantLight
import app.debugdesk.notebook.presentations.ui.colors.surfaceVariantLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.surfaceVariantLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.tertiaryContainerDark
import app.debugdesk.notebook.presentations.ui.colors.tertiaryContainerDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.tertiaryContainerDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.tertiaryContainerLight
import app.debugdesk.notebook.presentations.ui.colors.tertiaryContainerLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.tertiaryContainerLightMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.tertiaryDark
import app.debugdesk.notebook.presentations.ui.colors.tertiaryDarkHighContrast
import app.debugdesk.notebook.presentations.ui.colors.tertiaryDarkMediumContrast
import app.debugdesk.notebook.presentations.ui.colors.tertiaryLight
import app.debugdesk.notebook.presentations.ui.colors.tertiaryLightHighContrast
import app.debugdesk.notebook.presentations.ui.colors.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val ColorContrast.colorSchemeLight: ColorScheme
    get() = when (this) {
        ColorContrast.Standard -> lightScheme
        ColorContrast.Medium -> mediumContrastLightColorScheme
        ColorContrast.High -> highContrastLightColorScheme
    }

val ColorContrast.colorSchemeDark: ColorScheme
    get() = when (this) {
        ColorContrast.Standard -> darkScheme
        ColorContrast.Medium -> mediumContrastDarkColorScheme
        ColorContrast.High -> highContrastDarkColorScheme
    }

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)


@Composable
expect fun AppTheme(
    appAppearance: AppAppearance = AppAppearance(),
    content: @Composable () -> Unit
)

@Composable
expect fun AppThemePreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    colorContrast: ColorContrast = ColorContrast.Standard,
    content: @Composable () -> Unit
)
