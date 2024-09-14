package app.debugdesk.notebook.enummodel

sealed class NavigationClickAction {
    data class ThemeSwitch(val themeMode: ThemeMode) : NavigationClickAction()
    data class DynamicColorSwitch(val isDynamicColorEnable: Boolean) : NavigationClickAction()
    data object None : NavigationClickAction()
}