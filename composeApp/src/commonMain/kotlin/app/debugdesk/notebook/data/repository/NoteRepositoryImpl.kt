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

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    override val notes: StateFlow<List<Note>>
        get() = _notes

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
                is_pinned = note.isPinned,
            )
            noteQueries.lastInsertRowId().executeAsOne()
        } catch (exception: Exception) {
            Logcat.e("NoteRepositoryImpl", exception.message.toString())
            0
        }
    }

    override suspend fun getAllNotes() {
        try {
            _notes.tryEmit(
                noteQueries
                    .selectAll()
                    .executeAsList()
                    .map {
                        it.toNote()
                    }.sortedBy {
                        it.createdAt
                    }
            )
        } catch (exception: Exception) {
            Logcat.e("NoteRepositoryImpl", exception.message.toString())
        }
    }

    override suspend fun getNoteById(id: Long): Note? = withContext(Dispatchers.IO) {
        noteQueries.selectById(id).executeAsOneOrNull()?.toNote()
    }

    override suspend fun searchNotes(query: String): List<Note> = withContext(Dispatchers.IO) {
        noteQueries.searchNotes(query).executeAsList().map { it.toNote() }
    }

    override suspend fun getNotesByDateRange(startDate: Long, endDate: Long): List<Note> =
        withContext(Dispatchers.IO) {
            noteQueries.selectByDateRange(startDate, endDate).executeAsList().map { it.toNote() }
        }

    override suspend fun updateNote(
        note: Note
    ): Boolean = withContext(Dispatchers.IO) {
        noteQueries.updateNote(
            title = note.title,
            description = note.description,
            created_at = Clock.System.now().epochSeconds,
            last_modified = null,
            image = note.image,
            video = note.video,
            audio = note.audio,
            document = note.document,
            is_pinned = note.isPinned,
            id = note.id
        )
        noteQueries.changes().executeAsOne() > 0
    }

    override suspend fun deleteNoteById(id: Long): Boolean = withContext(Dispatchers.IO) {
        noteQueries.deleteById(id)
        noteQueries.changes().executeAsOne() > 0
    }

    override suspend fun addNote(note: Note) {
        _notes.tryEmit((this.notes.value + note).sortedBy { it.createdAt })
    }

    override suspend fun addNotes(notes: List<Note>) {
        _notes.tryEmit(notes)
    }

    override suspend fun deleteAllNotes(): Boolean = withContext(Dispatchers.IO) {
        noteQueries.deleteAll()
        noteQueries.changes().executeAsOne() > 0
    }

    override suspend fun toggleSelectionForAllNote(isSelectAll: Boolean) {
        _notes.value = notes.value.map { it.copy(isSelected = isSelectAll) }
    }

    override suspend fun pinAllSelectedNotes() {
        _notes.value = notes.value.map {
            if (it.isSelected) {
                val note = it.copy(isPinned = true)
                updateNote(note)
                note
            } else {
                it
            }
        }
    }

    override suspend fun unpinAllSelectedNotes() {
        _notes.value = notes.value.map {
            if (it.isSelected) {
                val note = it.copy(isPinned = false)
                updateNote(note)
                note
            } else {
                it
            }
        }
    }

    override suspend fun toggleSelectionForNote(note: Note) {
        _notes.value = notes.value.map {
            if (it.id == note.id) {
                note
            } else {
                it
            }
        }
    }

    override suspend fun deleteSelectedNote() {
        notes.value.filter { it.isSelected }.forEach {
            noteQueries.deleteById(it.id)
        }
        getAllNotes()
    }

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
            isPinned = is_pinned ?: false
        )
    }
}