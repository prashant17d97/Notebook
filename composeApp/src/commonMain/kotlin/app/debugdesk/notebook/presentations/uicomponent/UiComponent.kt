package app.debugdesk.notebook.presentations.uicomponent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

object UiComponent {

    @Composable
    fun NoteScaffold(
        modifier: Modifier = Modifier,
        fabIcon: ImageVector = Icons.Rounded.Add,
        showFab: Boolean = true,
        fabClick: () -> Unit = {},
        topAppBar: @Composable () -> Unit = {},
        floatingActionButton: @Composable () -> Unit = {
            AnimatedVisibility(
                visible = showFab,
                enter = scaleIn() + fadeIn() + slideInHorizontally { it },
                exit = scaleOut() + fadeOut() + slideOutHorizontally { it }
            ) {
                FloatingActionButton(onClick = fabClick) {
                    Icon(
                        imageVector = fabIcon,
                        contentDescription = fabIcon.name
                    )
                }
            }
        },
        bottomBar: @Composable () -> Unit = {},
        snackBarHost: @Composable () -> Unit = {},
        content: @Composable (PaddingValues) -> Unit
    ) {
        Scaffold(
            modifier = modifier.imePadding(),
            floatingActionButton = floatingActionButton,
            topBar = topAppBar,
            content = content,
            bottomBar = bottomBar,
            snackbarHost = snackBarHost
        )
    }
}