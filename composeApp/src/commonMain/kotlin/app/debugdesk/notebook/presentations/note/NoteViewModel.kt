package app.debugdesk.notebook.presentations.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.debugdesk.notebook.domain.model.Note
import app.debugdesk.notebook.domain.repositories.NoteRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel(), KoinComponent {
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

    suspend fun getNoteById(id: Long?): Note? {
        if (id == null) return null
        return noteRepository.getNoteById(id)
    }

    fun deleteNote(noteState: Note) {
        viewModelScope.launch {
            noteRepository.deleteNoteById(noteState.id)
        }
    }
}