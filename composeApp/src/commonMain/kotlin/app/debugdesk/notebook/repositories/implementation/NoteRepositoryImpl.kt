package app.debugdesk.notebook.repositories.implementation

import app.debugdesk.notebook.datamodel.Note
import app.debugdesk.notebook.repositories.interfaces.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NoteRepositoryImpl : NoteRepository {
    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    override val note: StateFlow<List<Note>>
        get() = _notes

    init {
        addNotes(
            listOf(
                Note(
                    id = 0,
                    title = "Prashant0",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 1,
                    title = "Prashant1",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 2,
                    title = "Prashant2",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 3,
                    title = "Prashant3",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 4,
                    title = "Prashant4",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 5,
                    title = "Prashant5",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 6,
                    title = "Prashant6",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 7,
                    title = "Prashant7",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 8,
                    title = "Prashant8",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
                Note(
                    id = 9,
                    title = "Prashant9",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ),
            )
        )
    }

    override fun addNote(note: Note) {
        _notes.tryEmit(_notes.value + note)
    }

    override fun appendNotes(notes: List<Note>) {
        _notes.tryEmit(notes)
    }

    override fun addNotes(notes: List<Note>) {
        _notes.tryEmit(_notes.value + notes)
    }

    override fun updateNote(note: Note) {
        val updatedValue = this.note.value.map { item ->
            if (item.id == note.id) {
                note
            } else {
                item
            }
        }
        _notes.tryEmit(updatedValue)
    }

    override fun deleteNote(note: Note) {
        _notes.tryEmit(this.note.value.filter { it.id != note.id })
    }

    override fun deleteNotes() {
        _notes.tryEmit(this.note.value.filter { !it.isSelected })
    }

    override fun deleteAllNotes() {
        _notes.tryEmit(emptyList())
    }


    override fun markAllNotesSelected() {
        _notes.tryEmit(note.value.map { it.copy(isSelected = true) })
    }

    override fun markAllNotesDeselected() {
        _notes.tryEmit(note.value.map { it.copy(isSelected = false) })
    }


    override fun pinAllSelectedNotes() {
        _notes.tryEmit(note.value.map {
            if (it.isSelected) {
                it.copy(isPinned = true)
            } else {
                it
            }
        })
    }

    override fun unPinAllSelectedNotes() {
        _notes.tryEmit(note.value.map {
            if (it.isSelected) {
                it.copy(isPinned = false)
            } else {
                it
            }
        })
    }
}