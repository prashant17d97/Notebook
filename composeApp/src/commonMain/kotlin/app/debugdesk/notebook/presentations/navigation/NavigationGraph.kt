package app.debugdesk.notebook.presentations.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import app.debugdesk.notebook.domain.model.Note
import app.debugdesk.notebook.presentations.home.Home
import app.debugdesk.notebook.presentations.note.CreateNote
import app.debugdesk.notebook.utils.log.Logcat

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Route.HomeScreen
    ) {
        composable<Route.HomeScreen> {
            Home(navHostController = navHostController)
        }
        composable<Route.NoteScreen> { backStackEntry ->
            val noteArgs: Route.NoteScreen = backStackEntry.toRoute()
            Logcat.d("NavGraph", noteArgs.toString())
            val note = if (noteArgs.title != null && noteArgs.description != null) {
                Note(title = noteArgs.title, description = noteArgs.description, id = noteArgs.id)
            } else {
                null
            }
            CreateNote(note = note, navHostController = navHostController)
        }
    }
}