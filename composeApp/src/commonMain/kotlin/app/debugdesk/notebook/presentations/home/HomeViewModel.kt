package app.debugdesk.notebook.presentations.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import app.debugdesk.notebook.domain.model.Note
import app.debugdesk.notebook.domain.repositories.AppStateOwner
import app.debugdesk.notebook.domain.repositories.NoteRepository
import app.debugdesk.notebook.presentations.navigation.Route
import app.debugdesk.notebook.utils.log.Logcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel(), KoinComponent {

    private val appStateOwner: AppStateOwner by inject()

    val notes = noteRepository.notes
    val showAllCheckedBox = appStateOwner.showAllCheckedBox

    init {
        noteRepository.notes.onEach {
            Logcat.e("HomeViewModel", "InitBlock:  $it")
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            noteRepository.getAllNotes()
        }
    }

    fun toggleSelectionForNote(note: Note) {
        noteRepository.toggleSelectionForNote(note)
    }

    fun enableAllCheckBox() {
        appStateOwner.setEnableAllCheckBox()
    }

    fun toggleSelectionForAllNote(isSelectAll: Boolean = false) {
        noteRepository.toggleSelectionForAllNote(isSelectAll = isSelectAll)
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
            if (notes.value.any { it.isSelected }) {
                noteRepository.pinAllSelectedNotes()
                enableAllCheckBox()
                toggleSelectionForAllNote(false)
            }
        }
    }

    fun deleteSelectedNote() {
        viewModelScope.launch {
            if (notes.value.any { it.isSelected }) {
                noteRepository.deleteSelectedNote()
                enableAllCheckBox()
                toggleSelectionForAllNote(false)
            }
        }
    }

    fun pinNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }
}