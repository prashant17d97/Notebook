package app.debugdesk.notebook.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("created_at")
    val createdAt: Long = 0L,
    @SerialName("last_modified")
    val lastModified: Long? = null,
    @SerialName("image")
    val image: ByteArray? = null,
    @SerialName("video")
    val video: ByteArray? = null,
    @SerialName("audio")
    val audio: ByteArray? = null,
    @SerialName("document")
    val document: ByteArray? = null,
    @SerialName("is_pinned")
    val isPinned: Boolean = false,
    @SerialName("isSelected")
    val isSelected: Boolean = true,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Note

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (createdAt != other.createdAt) return false
        if (lastModified != other.lastModified) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (video != null) {
            if (other.video == null) return false
            if (!video.contentEquals(other.video)) return false
        } else if (other.video != null) return false
        if (audio != null) {
            if (other.audio == null) return false
            if (!audio.contentEquals(other.audio)) return false
        } else if (other.audio != null) return false
        if (document != null) {
            if (other.document == null) return false
            if (!document.contentEquals(other.document)) return false
        } else if (other.document != null) return false
        if (isPinned != other.isPinned) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (lastModified?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + (video?.contentHashCode() ?: 0)
        result = 31 * result + (audio?.contentHashCode() ?: 0)
        result = 31 * result + (document?.contentHashCode() ?: 0)
        result = 31 * result + isPinned.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }
}