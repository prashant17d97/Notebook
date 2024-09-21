package app.debugdesk.notebook.data.repository

import app.debugdesk.database.Notebook
import app.debugdesk.database.NotebookDatabase
import app.debugdesk.notebook.domain.model.Note
import app.debugdesk.notebook.domain.repositories.NoteRepository
import app.debugdesk.notebook.utils.log.Logcat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class NoteRepositoryImpl(database: NotebookDatabase) : NoteRepository {
    private val noteQueries = database.notebookQueries

    // StateFlow for holding the list of notes
    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    override val notes: StateFlow<List<Note>>
        get() = _notes

    // Insert a new note into the database and update the StateFlow
    override suspend fun insertNote(
        note: Note
    ): Long = withContext(Dispatchers.IO) {
        try {
            Logcat.e("NoteRepositoryImpl", note.toString())
            noteQueries.insertNote(
                title = note.title,
                description = note.description,
                created_at = Clock.System.now().epochSeconds,
                last_modified = null,
                image = note.image,
                video = note.video,
                audio = note.audio,
                document = note.document,
                is_pinned = note.isPinned
            )
            val noteId = noteQueries.lastInsertRowId().executeAsOne()
            getAllNotes() // Refresh the list after insertion
            noteId
        } catch (exception: Exception) {
            Logcat.e("NoteRepositoryImpl", exception.message.toString())
            0
        }
    }

    // Fetch all notes from the database and emit to the StateFlow
    override suspend fun getAllNotes(): List<Note> = withContext(Dispatchers.IO) {
        try {
            val notesList = noteQueries
                .selectAll()
                .executeAsList()
                .map { it.toNote() }
                .sortedBy { it.createdAt }

            withContext(Dispatchers.Main) {
                Logcat.e("NoteRepositoryImpl", "Emitting notes: $notesList")
                _notes.value = notesList // Make sure this is emitting
            }
            notesList
        } catch (exception: Exception) {
            Logcat.e("NoteRepositoryImpl", exception.message.toString())
            emptyList()
        }
    }

    // Fetch a note by its ID
    override suspend fun getNoteById(id: Long): Note? = withContext(Dispatchers.IO) {
        noteQueries.selectById(id).executeAsOneOrNull()?.toNote()
    }

    // Search notes by query and return the matching list
    override suspend fun searchNotes(query: String): List<Note> = withContext(Dispatchers.IO) {
        noteQueries.searchNotes(query).executeAsList().map { it.toNote() }
    }

    // Fetch notes within a specified date range
    override suspend fun getNotesByDateRange(startDate: Long, endDate: Long): List<Note> =
        withContext(Dispatchers.IO) {
            noteQueries.selectByDateRange(startDate, endDate).executeAsList().map { it.toNote() }
        }

    // Update an existing note in the database and refresh the StateFlow
    override suspend fun updateNote(
        note: Note
    ): Boolean = withContext(Dispatchers.IO) {
        noteQueries.updateNote(
            title = note.title,
            description = note.description,
            created_at = note.createdAt, // Keep the original creation time
            last_modified = Clock.System.now().epochSeconds, // Update the last modified timestamp
            image = note.image,
            video = note.video,
            audio = note.audio,
            document = note.document,
            is_pinned = note.isPinned,
            id = note.id
        )
        getAllNotes() // Refresh the list after update
        if (note.isSelected){
            toggleSelectionForNote(note)
        }
        noteQueries.changes().executeAsOne() > 0
    }

    // Delete a note by its ID and update the StateFlow
    override suspend fun deleteNoteById(id: Long): Boolean = withContext(Dispatchers.IO) {
        noteQueries.deleteById(id)
        getAllNotes() // Refresh the list after deletion
        noteQueries.changes().executeAsOne() > 0
    }

    // Add a note to the in-memory list without persisting it in the database
    override fun addNote(note: Note) {
        _notes.value = (_notes.value + note).sortedBy { it.createdAt }
    }

    // Add multiple notes to the in-memory list
    override fun addNotes(notes: List<Note>) {
        _notes.value = notes
    }

    // Delete all notes from the database and refresh the StateFlow
    override suspend fun deleteAllNotes(): Boolean = withContext(Dispatchers.IO) {
        noteQueries.deleteAll()
        getAllNotes() // Refresh the list after deletion
        noteQueries.changes().executeAsOne() > 0
    }

    // Toggle selection for all notes and update the StateFlow
    override fun toggleSelectionForAllNote(isSelectAll: Boolean) {
        _notes.value = notes.value.map { it.copy(isSelected = isSelectAll) }
    }

    // Pin all selected notes and refresh the StateFlow
    override suspend fun pinAllSelectedNotes() {
        notes.value.filter { it.isSelected }.forEach { note ->
            updateNote(note.copy(isPinned = true)) // Properly update each selected note
        }
        getAllNotes() // Refresh the list after pinning
    }

    // Unpin all selected notes and refresh the StateFlow
    override suspend fun unpinAllSelectedNotes() {
        notes.value.filter { it.isSelected }.forEach { note ->
            updateNote(note.copy(isPinned = false)) // Properly update each selected note
        }
        getAllNotes() // Refresh the list after unpinning
    }

    // Toggle selection for a specific note
    override fun toggleSelectionForNote(note: Note) {
        val updatedData = notes.value.map { item ->
            if (item.id == note.id) {
                note
            } else {
                item
            }
        }
        _notes.tryEmit(updatedData)
    }

    // Delete all selected notes and refresh the StateFlow
    override suspend fun deleteSelectedNote() {
        notes.value.filter { it.isSelected }.forEach { note ->
            noteQueries.deleteById(note.id)
        }
        getAllNotes() // Refresh the list after deletion
    }

    // Helper function to convert database object to domain model
    private fun Notebook.toNote(): Note {
        return Note(
            id = id,
            title = title,
            description = description,
            createdAt = created_at,
            lastModified = last_modified,
            image = image,
            video = video,
            audio = audio,
            document = document,
            isPinned = is_pinned ?: false,
            isSelected = false // Default to unselected
        )
    }
}
