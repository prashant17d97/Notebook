package app.debugdesk.notebook.presentations.note

import androidx.lifecycle.ViewModel
import app.debugdesk.notebook.datamodel.Note
import app.debugdesk.notebook.repositories.interfaces.NoteRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteViewModel : ViewModel(), KoinComponent {
    private val noteRepository: NoteRepository by inject()

    fun saveNote(noteState: Note) {
        noteRepository.addNote(noteState)
    }

    fun updateNote(noteState: Note) {
        noteRepository.updateNote(noteState)
    }

    fun deleteNote(noteState: Note) {
        noteRepository.deleteNote(noteState)
    }
}