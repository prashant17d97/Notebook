package app.debugdesk.notebook.datamodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Note(
    @SerialName("id")
    val id: Int = Random.nextInt(),
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("isSelected")
    val isSelected: Boolean = false,
    @SerialName("isPinned")
    val isPinned: Boolean = false
)
