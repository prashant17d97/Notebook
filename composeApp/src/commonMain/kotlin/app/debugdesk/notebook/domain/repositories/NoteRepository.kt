package app.debugdesk.notebook.domain.repositories

import app.debugdesk.notebook.domain.model.Note
import kotlinx.coroutines.flow.StateFlow

interface NoteRepository {

    val notes: StateFlow<List<Note>>

    suspend fun insertNote(note: Note): Long

    suspend fun getAllNotes(): List<Note>

    suspend fun getNoteById(id: Long): Note?

    suspend fun searchNotes(query: String): List<Note>

    suspend fun getNotesByDateRange(startDate: Long, endDate: Long): List<Note>

    suspend fun deleteNoteById(id: Long): Boolean

    fun toggleSelectionForAllNote(isSelectAll: Boolean)

    suspend fun unpinAllSelectedNotes()

    fun toggleSelectionForNote(note: Note)

    suspend fun deleteSelectedNote()

    fun addNote(note: Note)

    fun addNotes(notes: List<Note>)

    suspend fun updateNote(note: Note): Boolean

    suspend fun deleteAllNotes(): Boolean

    suspend fun pinAllSelectedNotes()
}
