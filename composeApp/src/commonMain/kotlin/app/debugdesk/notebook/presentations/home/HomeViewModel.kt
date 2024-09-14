package app.debugdesk.notebook.presentations.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import app.debugdesk.notebook.datamodel.Note
import app.debugdesk.notebook.navigation.Route
import app.debugdesk.notebook.repositories.interfaces.NoteRepository
import app.debugdesk.notebook.stateowners.appstatesowner.AppStateOwner
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val appStateOwner: AppStateOwner by inject()
    private val noteRepository: NoteRepository by inject()

    val notes = noteRepository.note
    val showAllCheckedBox = appStateOwner.showAllCheckedBox
    fun modifiedNote(note: Note) {
        noteRepository.updateNote(note)
    }

    fun enableAllCheckBox() {
        appStateOwner.setEnableAllCheckBox()
    }

    fun markSelectAllNote(select: Boolean = false) {
        if (select) {
            noteRepository.markAllNotesSelected()
        } else {
            noteRepository.markAllNotesDeselected()
        }
    }

    fun onNoteClick(navHostController: NavHostController, it: Note) {
        navHostController.navigate(
            Route.NoteScreen(
                id=it.id,
                title = it.title,
                description = it.description,
            )
        )
    }

    fun pinSelectedNote() {
        noteRepository.pinAllSelectedNotes()
        enableAllCheckBox()
        markSelectAllNote(false)
    }

    fun deleteSelectedNote() {
        noteRepository.deleteNotes()
        enableAllCheckBox()
        markSelectAllNote(false)
    }
}