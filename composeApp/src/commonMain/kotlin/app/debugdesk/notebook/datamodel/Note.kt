package app.debugdesk.notebook.datamodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("isSelected")
    val isSelected: Boolean = false,
    @SerialName("isPinned")
    val isPinned: Boolean = false
)
