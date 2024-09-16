package app.debugdesk.notebook.presentations.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
@Preview
fun NoteBookApp() {
    val navHostController = rememberNavController()
    val appStateOwner: AppStateOwner = koinInject()
    val showFab by appStateOwner.showFab.collectAsState()
    val homeViewModel: HomeViewModel = koinInject()
    val noteVM: NoteVM = koinInject()
    val showAllCheckedBox by homeViewModel.showAllCheckedBox.collectAsState()
    val appearance by noteVM.appearance.collectAsState()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val isHomeScreen by rememberUpdatedState(navBackStackEntry?.destination?.route == Route.HomeScreen::class.qualifiedName)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AppTheme(appAppearance = appearance) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NavigationDrawerItems(
                            appAppearance = appearance, onClick = noteVM::updateAppearance
                        )
                    }
                }
            },
            gesturesEnabled = true
        ) {
            NoteScaffold(
                showFab = showFab,
                fabClick = {
                    navHostController.navigate(Route.NoteScreen())
                },
                topAppBar = {
                    TopBar(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    isHomeScreen: Boolean = false,
    title: String = "NoteBook",
    onActionClick: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    onPinnedClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {
        AnimatedVisibility(show) {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    Icons.AutoMirrored.Rounded.ArrowBack.name,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    },
) {

    val icon by rememberUpdatedState(
        if (isHomeScreen)
            Icons.Rounded.Menu
        else
            Icons.AutoMirrored.Rounded.KeyboardArrowLeft
    )
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        TopAppBar(
            modifier = modifier.padding(end = 14.dp),
            title = {
                AnimatedVisibility(!show) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 12.dp, alignment = Alignment.CenterHorizontally
                        )
                    ) {
                        Surface(color = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(30.dp).clip(CircleShape)
                                .clickable { onActionClick() }) {

                            Icon(
                                imageVector = icon,
                                contentDescription = icon.name,
                                modifier = Modifier.padding(4.dp),
                            )
                        }
                        Text(text = title)
                    }

                }
            }, actions = {
                AnimatedVisibility(show) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 6.dp,
                            alignment = Alignment.CenterHorizontally
                        )
                    ) {
                        IconButton(
                            onClick = onPinnedClick,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_pin_outlined),
                                contentDescription = "Pinned",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier=Modifier.size(24.dp)
                            )
                        }
                        IconButton(
                            onClick = onDeleteClick,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                Icons.Rounded.Delete.name,
                            )
                        }
                    }
                }
            },
            navigationIcon = navigationIcon
        )
    }
}


@Composable
fun NavigationDrawerItems(
    modifier: Modifier = Modifier,
    appAppearance: AppAppearance,
    onClick: (AppAppearance) -> Unit = {},
) {
    val colorContrastErrorMsg = stringResource(Res.string.color_contrast_not_supported)
    var isThemeModeExpended by rememberSaveable {
        mutableStateOf(false)
    }
    var isColorContrastExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val cornerRadius: Dp by animateDpAsState(
        targetValue = if (isThemeModeExpended || isColorContrastExpanded) 20.dp else 50.dp // Adjust 50.dp for CircleShape approximation
    )
    Column(
        modifier = modifier.padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(
            space = 8.dp, alignment = Alignment.CenterVertically
        ), horizontalAlignment = Alignment.Start
    ) {
        AnimatedVisibility(isAndroid) {
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
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding) //padding between items
            )
        }


        Column(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                .clip(RoundedCornerShape(cornerRadius)).background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                )


        ) {
            NavigationDrawerItem(
                label = { Text(text = stringResource(Res.string.theme_mode)) },
                selected = true,
                onClick = { isThemeModeExpended = !isThemeModeExpended },
                icon = {
                    Icon(painter = painterResource(Res.drawable.ic_dark_mode.takeIf {
                        appAppearance.themeMode.isDakTheme
                    } ?: Res.drawable.ic_light_mode),
                        contentDescription = "Dynamic Color",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer)
                },
                badge = {
                    Text(stringResource(appAppearance.themeMode.value))
                },

                )
            AnimatedVisibility(isThemeModeExpended) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(NavigationDrawerItemDefaults.ItemPadding),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterVertically,
                    ),
                    horizontalAlignment = Alignment.Start
                ) {
                    ThemeMode.entries.forEach {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                isThemeModeExpended = false
                                onClick(appAppearance.copy(themeMode = it))
                            }) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(it.value),
                                textAlign = TextAlign.Start,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }


        Column(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                .clip(RoundedCornerShape(cornerRadius)).background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                )
        ) {
            NavigationDrawerItem(
                label = { Text(text = stringResource(Res.string.color_contrast)) },
                selected = true,
                onClick = {
                    if (!appAppearance.useSystemPalette) {
                        isColorContrastExpanded = !isColorContrastExpanded
                    } else {
                        toastMsg(colorContrastErrorMsg)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_contrast),
                        contentDescription = "Dynamic Color",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                badge = {
                    Text(stringResource(appAppearance.colorContrast.value))
                },

                )
            AnimatedVisibility(isColorContrastExpanded) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(NavigationDrawerItemDefaults.ItemPadding),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterVertically,
                    ),
                    horizontalAlignment = Alignment.Start
                ) {
                    ColorContrast.entries.forEach {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                isColorContrastExpanded = false
                                onClick(appAppearance.copy(colorContrast = it))
                            }) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(it.value),
                                textAlign = TextAlign.Start,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}