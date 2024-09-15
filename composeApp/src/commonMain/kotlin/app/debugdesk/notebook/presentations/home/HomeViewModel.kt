package app.debugdesk.notebook.presentations.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import app.debugdesk.notebook.domain.model.Note
import app.debugdesk.notebook.domain.repositories.AppStateOwner
import app.debugdesk.notebook.domain.repositories.NoteRepository
import app.debugdesk.notebook.presentations.navigation.Route
import app.debugdesk.notebook.utils.log.Logcat
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val appStateOwner: AppStateOwner by inject()
    private val noteRepository: NoteRepository by inject()

    val notes: StateFlow<List<Note>> = noteRepository.notes
    val showAllCheckedBox = appStateOwner.showAllCheckedBox

    init {
        viewModelScope.launch {
            noteRepository.getAllNotes()
        }
    }

    fun toggleSelectionForNote(note: Note) {
        viewModelScope.launch {
            noteRepository.toggleSelectionForNote(note)
            Logcat.e("HomeViewModel", note.toString())
        }
    }

    fun enableAllCheckBox() {
        appStateOwner.setEnableAllCheckBox()
    }

    fun toggleSelectionForAllNote(isSelectAll: Boolean = false) {
        Logcat.e("HomeViewModel", "toggleSelectionForAllNote:--->$isSelectAll")
        viewModelScope.launch {
            noteRepository.toggleSelectionForAllNote(isSelectAll = isSelectAll)
        }
    }

    fun onNoteClick(navHostController: NavHostController, it: Note) {
        navHostController.navigate(
            Route.NoteScreen(
                id = it.id,
                title = it.title,
                description = it.description,
            )
        )
    }

    fun pinSelectedNote() {
        viewModelScope.launch {
            noteRepository.pinAllSelectedNotes()
            enableAllCheckBox()
            toggleSelectionForAllNote(false)
        }
    }

    fun deleteSelectedNote() {
        viewModelScope.launch {
            noteRepository.deleteSelectedNote()
            enableAllCheckBox()
            toggleSelectionForAllNote(false)
        }
    }

    fun pinNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }
}