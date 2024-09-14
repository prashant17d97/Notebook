package app.debugdesk.notebook.datamodel

import app.debugdesk.notebook.enummodel.AppColorScheme
import app.debugdesk.notebook.enummodel.ColorContrast
import app.debugdesk.notebook.enummodel.ThemeMode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppAppearance(
    @SerialName("colorContrast")
    val colorContrast: ColorContrast = ColorContrast.Standard,
    @SerialName("font")
    val font: String = "",
    @SerialName("themeMode")
    val themeMode: ThemeMode = ThemeMode.System,
    @SerialName("language")
    val language: String = "",
    @SerialName("theme")
    val appColorScheme: AppColorScheme = AppColorScheme.Default,
    @SerialName("useSystemPalette")
    val useSystemPalette: Boolean = false,
)