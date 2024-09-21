package app.debugdesk.notebook.presentations.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data class NoteScreen(
        @SerialName("id")
        val id: Long = 0L,
        @SerialName("title")
        val title: String? = null,
        @SerialName("description")
        val description: String? = null
    ) : Route()
}