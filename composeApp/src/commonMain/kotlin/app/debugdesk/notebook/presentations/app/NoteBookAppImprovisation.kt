package app.debugdesk.notebook.presentations.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.debugdesk.notebook.domain.model.AppAppearance
import app.debugdesk.notebook.domain.model.ColorContrast
import app.debugdesk.notebook.domain.model.ThemeMode
import app.debugdesk.notebook.domain.model.isDakTheme
import app.debugdesk.notebook.domain.repositories.AppStateOwner
import app.debugdesk.notebook.presentations.home.HomeViewModel
import app.debugdesk.notebook.presentations.navigation.NavigationGraph
import app.debugdesk.notebook.presentations.navigation.Route
import app.debugdesk.notebook.presentations.ui.theme.AppTheme
import app.debugdesk.notebook.presentations.uicomponent.UiComponent.NoteScaffold
import app.debugdesk.notebook.utils.SharedObjects.isAndroid
import app.debugdesk.notebook.utils.SharedObjects.toastMsg
import kotlinx.coroutines.launch
import notebook.composeapp.generated.resources.Res
import notebook.composeapp.generated.resources.color_contrast
import notebook.composeapp.generated.resources.color_contrast_not_supported
import notebook.composeapp.generated.resources.dynamic_color
import notebook.composeapp.generated.resources.ic_contrast
import notebook.composeapp.generated.resources.ic_dark_mode
import notebook.composeapp.generated.resources.ic_dynamic_color
import notebook.composeapp.generated.resources.ic_light_mode
import notebook.composeapp.generated.resources.icon_pin_outlined
import notebook.composeapp.generated.resources.theme_mode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

/**
 * The main composable function for the NoteBook application.
 * Sets up the navigation controller, handles state management, and applies the app theme.
 */
@Composable
@Preview
private fun NoteBookAppImprovisation() {
    // Remember the NavController to manage navigation within the app
    val navHostController = rememberNavController()

    // Dependency injection for app state and view models
    val appStateOwner: AppStateOwner = koinInject()
    val homeViewModel: HomeViewModel = koinInject()
    val noteVM: NoteVM = koinInject()

    // Collecting state from view models
    val showFab by appStateOwner.showFab.collectAsState()
    val showAllCheckedBox by homeViewModel.showAllCheckedBox.collectAsState()
    val appearance by noteVM.appearance.collectAsState()

    // Observing the current back stack entry to determine the current route
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val isHomeScreen =
        navBackStackEntry?.destination?.route == Route.HomeScreen::class.qualifiedName

    // Setting up the drawer state for the navigation drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Applying the app theme based on the current appearance settings
    AppTheme(appAppearance = appearance) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawerItems2(
                        appAppearance = appearance,
                        onClick = noteVM::updateAppearance
                    )
                }
            }
        ) {
            NoteScaffold(
                showFab = showFab,
                fabClick = {
                    navHostController.navigate(Route.NoteScreen())
                },
                topAppBar = {
                    TopBar2(
                        show = showAllCheckedBox,
                        isHomeScreen = isHomeScreen,
                        onActionClick = {
                            if (isHomeScreen) {
                                scope.launch { drawerState.open() }
                            } else {
                                navHostController.navigateUp()
                            }
                        },
                        onNavigationClick = {
                            homeViewModel.enableAllCheckBox()
                            homeViewModel.toggleSelectionForAllNote()
                        },
                        onDeleteClick = {
                            homeViewModel.deleteSelectedNote()
                        },
                        onPinnedClick = {
                            homeViewModel.pinSelectedNote()
                        }
                    )
                }
            ) {
                NavigationGraph(
                    modifier = Modifier.padding(it),
                    navHostController = navHostController
                )
            }
        }
    }
}

/**
 * A composable function that represents the top app bar in the application.
 *
 * @param modifier Modifier to be applied to the TopBar.
 * @param show Determines whether certain UI elements should be shown.
 * @param isHomeScreen Indicates if the current screen is the home screen.
 * @param title The title displayed in the TopBar.
 * @param onActionClick Callback for action button click.
 * @param onNavigationClick Callback for navigation icon click.
 * @param onPinnedClick Callback for the pin action.
 * @param onDeleteClick Callback for the delete action.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar2(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    isHomeScreen: Boolean = false,
    title: String = "NoteBook",
    onActionClick: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    onPinnedClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    // Determine the navigation icon based on the current screen and UI state
    val navigationIcon: @Composable (() -> Unit) = if (show) {
        {}
    } else {
        val icon = if (isHomeScreen) Icons.Rounded.Menu else Icons.AutoMirrored.Rounded.ArrowBack
        {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = icon,
                    contentDescription = icon.name,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }

    // Surface to provide elevation and background for the TopAppBar
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        TopAppBar(
            modifier = modifier.padding(end = 14.dp),
            title = {
                if (!show) {
                    Text(text = title)
                }
            },
            navigationIcon = navigationIcon,
            actions = {
                if (show) {
                    // Actions displayed when 'show' is true
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 6.dp,
                            alignment = Alignment.CenterHorizontally
                        )
                    ) {
                        // Pin action button
                        IconButton(
                            onClick = onPinnedClick,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_pin_outlined),
                                contentDescription = "Pinned",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        // Delete action button
                        IconButton(
                            onClick = onDeleteClick,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = Icons.Rounded.Delete.name,
                            )
                        }
                    }
                }
            }
        )
    }
}

/**
 * A composable function that displays the navigation drawer items.
 * It includes expandable items for theme mode and color contrast settings.
 *
 * @param modifier Modifier to be applied to the NavigationDrawerItems.
 * @param appAppearance The current appearance settings of the app.
 * @param onClick Callback when an appearance setting is changed.
 */
@Composable
private fun NavigationDrawerItems2(
    modifier: Modifier = Modifier,
    appAppearance: AppAppearance,
    onClick: (AppAppearance) -> Unit = {},
) {
    // Error message when color contrast is not supported
    val colorContrastErrorMsg = stringResource(Res.string.color_contrast_not_supported)

    // State variables to manage the expansion of expandable items
    var isThemeModeExpanded by rememberSaveable { mutableStateOf(false) }
    var isColorContrastExpanded by rememberSaveable { mutableStateOf(false) }


    // Column to hold all navigation drawer items
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Dynamic color switch item (visible only on Android)
        if (isAndroid) {
            NavigationDrawerItem(
                label = { Text(text = stringResource(Res.string.dynamic_color)) },
                selected = true,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_dynamic_color),
                        contentDescription = "Dynamic Color"
                    )
                },
                badge = {
                    Switch(checked = appAppearance.useSystemPalette, onCheckedChange = {
                        onClick(appAppearance.copy(useSystemPalette = it))
                    })
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }

        // Expandable item for theme mode selection
        ExpandableNavigationDrawerItem(
            title = stringResource(Res.string.theme_mode),
            iconPainter = painterResource(
                if (appAppearance.themeMode.isDakTheme) Res.drawable.ic_dark_mode else Res.drawable.ic_light_mode
            ),
            badgeText = stringResource(appAppearance.themeMode.value),
            isExpanded = isThemeModeExpanded,
            onExpandToggle = { isThemeModeExpanded = !isThemeModeExpanded },
            options = ThemeMode.entries.map { mode ->
                stringResource(mode.value) to {
                    isThemeModeExpanded = false
                    onClick(appAppearance.copy(themeMode = mode))
                }
            },
        )

        // Expandable item for color contrast selection
        ExpandableNavigationDrawerItem(
            title = stringResource(Res.string.color_contrast),
            iconPainter = painterResource(Res.drawable.ic_contrast),
            badgeText = stringResource(appAppearance.colorContrast.value),
            isExpanded = isColorContrastExpanded,
            onExpandToggle = {
                if (!appAppearance.useSystemPalette) {
                    isColorContrastExpanded = !isColorContrastExpanded
                } else {
                    toastMsg(colorContrastErrorMsg)
                }
            },
            options = ColorContrast.entries.map { contrast ->
                stringResource(contrast.value) to {
                    isColorContrastExpanded = false
                    onClick(appAppearance.copy(colorContrast = contrast))
                }
            },
        )
    }
}

/**
 * A reusable composable function for creating expandable navigation drawer items.
 *
 * @param title The title of the navigation item.
 * @param iconPainter The painter for the navigation item icon.
 * @param badgeText The text displayed in the badge.
 * @param isExpanded Indicates whether the item is expanded.
 * @param onExpandToggle Callback when the expand state is toggled.
 * @param options A list of options to display when expanded.
 */
@Composable
fun ExpandableNavigationDrawerItem(
    title: String,
    iconPainter: Painter,
    badgeText: String,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    options: List<Pair<String, () -> Unit>>,
) {
    val colorCornerRadius: Dp by animateDpAsState(
        targetValue = if (isExpanded) 20.dp else 50.dp
    )
    // Column to hold the navigation item and its expandable content
    Column(
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding)
            .clip(RoundedCornerShape(colorCornerRadius))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        // The main navigation item
        NavigationDrawerItem(
            label = { Text(text = title) },
            selected = true,
            onClick = onExpandToggle,
            icon = {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            },
            badge = {
                Text(badgeText)
            }
        )
        // The expandable content
        AnimatedVisibility (visible = isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NavigationDrawerItemDefaults.ItemPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Options displayed when the item is expanded
                options.forEach { (optionText, onClickOption) ->
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClickOption
                    ) {
                        Text(
                            text = optionText,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}
