package app.debugdesk.notebook.enummodel

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import notebook.composeapp.generated.resources.Res
import notebook.composeapp.generated.resources.dark
import notebook.composeapp.generated.resources.high
import notebook.composeapp.generated.resources.light
import notebook.composeapp.generated.resources.medium
import notebook.composeapp.generated.resources.standard
import notebook.composeapp.generated.resources.system
import org.jetbrains.compose.resources.StringResource

@Serializable
enum class AppColorScheme {
    @SerialName("Default")
    Default,
}

@Serializable
enum class ColorContrast(val value: StringResource) {
    @SerialName("High")
    High(Res.string.high),

    @SerialName("Medium")
    Medium(Res.string.medium),

    @SerialName("Standard")
    Standard(Res.string.standard)
}


@Serializable
enum class ThemeMode(val value: StringResource) {
    @SerialName("System")
    System(Res.string.system),

    @SerialName("Light")
    Light(Res.string.light),

    @SerialName("Dark")
    Dark(Res.string.dark)
}

sealed class AppearnceEnumsClick {
    data class ThemeModeClick(val themeMode: ThemeMode) : AppearnceEnumsClick()
    data class ColorContrastClick(val colorContrast: ColorContrast) : AppearnceEnumsClick()
    data class ThemeClick(val appColorScheme: AppColorScheme) : AppearnceEnumsClick()
    data class DynamicColorClick(val dynamicColor: Boolean) : AppearnceEnumsClick()

}

val ThemeMode.isDakTheme: Boolean
    @Composable
    get() = when (this) {
        ThemeMode.System -> isSystemInDarkTheme()
        ThemeMode.Light -> false
        ThemeMode.Dark -> true
    }
