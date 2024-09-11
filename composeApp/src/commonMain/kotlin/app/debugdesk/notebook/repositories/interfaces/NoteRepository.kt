package app.debugdesk.notebook.repositories.interfaces

import app.debugdesk.notebook.datamodel.Note
import kotlinx.coroutines.flow.StateFlow

interface NoteRepository {

    val note: StateFlow<List<Note>>

    fun addNote(note: Note)

    fun appendNotes(notes: List<Note>)

    fun addNotes(notes: List<Note>)

    fun updateNote(note: Note)

    fun deleteNote(note: Note)

    fun deleteNotes()

    fun deleteAllNotes()

    fun markAllNotesSelected()

    fun markAllNotesDeselected()

    fun pinAllSelectedNotes()

    fun unPinAllSelectedNotes()
}