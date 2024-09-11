package app.debugdesk.notebook.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data class NoteScreen(
        @SerialName("id")
        val id: Int = 0,
        @SerialName("title")
        val title: String? = null,
        @SerialName("description")
        val description: String? = null
    ) : Route()
}