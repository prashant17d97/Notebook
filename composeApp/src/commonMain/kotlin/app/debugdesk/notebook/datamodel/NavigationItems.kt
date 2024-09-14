package app.debugdesk.notebook.datamodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import app.debugdesk.notebook.enummodel.NavigationClickAction
import app.debugdesk.notebook.enummodel.ThemeMode
import notebook.composeapp.generated.resources.Res
import notebook.composeapp.generated.resources.dynamic_color
import notebook.composeapp.generated.resources.ic_dark_mode
import notebook.composeapp.generated.resources.ic_dynamic_color
import notebook.composeapp.generated.resources.ic_light_mode
import notebook.composeapp.generated.resources.theme_mode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class NavigationItems(
    val title: String,
    val trailingIcon: Painter,
    val unselectedIcon: Painter,
    val iconSwitchingEnable: Boolean = false,
    val isSelectedItem: Boolean = false,
    val clickAction: NavigationClickAction = NavigationClickAction.None,
)


val navigationItems: List<NavigationItems>
    @Composable
    get() = listOf(
        NavigationItems(
            title = stringResource(Res.string.theme_mode),
            trailingIcon = painterResource(Res.drawable.ic_dark_mode),
            unselectedIcon = painterResource(Res.drawable.ic_light_mode),
            iconSwitchingEnable = true,
            clickAction = NavigationClickAction.ThemeSwitch(ThemeMode.System)
        ),
        NavigationItems(
            title = stringResource(Res.string.dynamic_color),
            trailingIcon = painterResource(Res.drawable.ic_dynamic_color),
            unselectedIcon = painterResource(Res.drawable.ic_dynamic_color),
            clickAction = NavigationClickAction.DynamicColorSwitch(isDynamicColorEnable = false)
        )
    )