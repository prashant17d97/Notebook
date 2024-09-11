package app.debugdesk.notebook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Person
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
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import app.debugdesk.notebook.navigation.NavigationGraph
import app.debugdesk.notebook.navigation.Route
import app.debugdesk.notebook.presentations.home.HomeViewModel
import app.debugdesk.notebook.stateowners.appstatesowner.AppStateOwner
import app.debugdesk.notebook.ui.theme.AppTheme
import app.debugdesk.notebook.utils.UiComponent.NoteScaffold
import kotlinx.coroutines.launch
import notebook.composeapp.generated.resources.Res
import notebook.composeapp.generated.resources.icon_pinned
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun NoteBookApp() {
    
    val navHostController = rememberNavController()
    val appStateOwner: AppStateOwner = koinInject()
    val showFab by appStateOwner.showFab.collectAsState()
    val homeViewModel: HomeViewModel = koinInject()
    val showAllCheckedBox by homeViewModel.showAllCheckedBox.collectAsState()

    val items = listOf(
        NavigationItems(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItems(
            title = "Info",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        ),
        NavigationItems(
            title = "Edit",
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
            badgeCount = 105
        ),
        NavigationItems(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AppTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                label = { Text(text = item.title) },
                                selected = index == selectedItemIndex,
                                onClick = {
                                    selectedItemIndex = index
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                },
                                badge = {  // Show Badge
                                    item.badgeCount?.let {
                                        Text(text = item.badgeCount.toString())
                                    }
                                },
                                modifier = Modifier
                                    .padding(NavigationDrawerItemDefaults.ItemPadding) //padding between items
                            )
                        }
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
                        actionIcon = Icons.Rounded.Person,
                        onActionClick = {
                            scope.launch { drawerState.open() }
                        },
                        onNavigationClick = {
                            homeViewModel.enableAllCheckBox()
                            homeViewModel.markSelectAllNote()
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
    actionIcon: ImageVector = Icons.Rounded.Person,
    show: Boolean = false,
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
                    Icons.AutoMirrored.Rounded.ArrowBack.name
                )
            }
        }
    },
) {
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        TopAppBar(
            modifier = modifier.padding(end = 14.dp),
            title = {
                AnimatedVisibility(!show) {
                    Text(text = title)
                }
            },
            actions = {
                AnimatedVisibility(!show) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.size(30.dp).clip(CircleShape)
                            .clickable { onActionClick() }
                    ) {

                        Icon(
                            imageVector = actionIcon,
                            contentDescription = actionIcon.name,
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                }
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
                                painter = painterResource(Res.drawable.icon_pinned),
                                contentDescription = "Pinned",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier=Modifier.rotate(90f)
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

data class NavigationItems(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
)
