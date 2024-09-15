package app.debugdesk.notebook.presentations.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.debugdesk.notebook.domain.model.Note
import app.debugdesk.notebook.domain.repositories.NoteRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteViewModel : ViewModel(), KoinComponent {
    private val noteRepository: NoteRepository by inject()

    fun saveNote(noteState: Note) {
        viewModelScope.launch {
            noteRepository.insertNote(noteState)
        }
    }

    fun updateNote(noteState: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(noteState)
        }
    }

    fun deleteNote(noteState: Note) {
        viewModelScope.launch {
            noteRepository.deleteNoteById(noteState.id)
        }
    }
}