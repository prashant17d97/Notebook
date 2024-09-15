package app.debugdesk.notebook.presentations.note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.debugdesk.notebook.domain.model.Note
import app.debugdesk.notebook.domain.repositories.AppStateOwner
import org.koin.compose.koinInject

@Composable
fun CreateNote(
    modifier: Modifier = Modifier, note: Note?,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel = koinInject()
) {
    var noteState by remember { mutableStateOf(note ?: Note(title = "", description = "")) }
    val isNewEntry by rememberUpdatedState(note == null)
    val appStateOwner: AppStateOwner = koinInject()
    var isEditable by remember { mutableStateOf(false) }
    DisposableEffect(Unit) {
        appStateOwner.setShowFab(false)
        onDispose {
            appStateOwner.setShowFab(true)
        }
    }
    Column(
        modifier = modifier.fillMaxSize().imePadding().padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())
        ) {
            AnimatedVisibility(visible = !isEditable && !isNewEntry) {
                DisableText(note = noteState)
            }
            AnimatedVisibility(visible = isEditable || isNewEntry) {
                EditableField(note = noteState) {
                    noteState = it
                }
            }

        }

        AnimatedVisibility(
            visible = isEditable || isNewEntry,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Button(
                    onClick = {
                        if (isNewEntry) {
                            navHostController.navigateUp()
                        } else {
                            isEditable = false
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Button(
                    enabled = noteState.title.isNotBlank() && noteState.description.isNotBlank(),
                    onClick = {
                        if (isNewEntry) {
                            noteViewModel.saveNote(noteState)
                        } else {
                            noteViewModel.updateNote(noteState)
                        }
                        navHostController.navigateUp()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }
            }
        }

        AnimatedVisibility(
            visible = !isEditable && !isNewEntry,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Button(
                    onClick = {
                        noteViewModel.deleteNote(noteState)
                        navHostController.navigateUp()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete")
                }

                Button(
                    onClick = {
                        isEditable = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit")
                }
            }
        }
    }
}


@Composable
private fun DisableText(modifier: Modifier = Modifier, note: Note) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top)) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = note.description,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun EditableField(
    modifier: Modifier = Modifier, note: Note,
    onValueChange: (Note) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top)) {
        OutlinedTextField(
            value = note.title,
            onValueChange = {
                onValueChange(note.copy(title = it))
            },
            modifier = modifier.fillMaxWidth(),
            maxLines = 1,
            placeholder = {
                Text(text = "Title")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrectEnabled = true,
                hintLocales = LocaleList(
                    Locale("en"),
                    Locale("hi"),
                    Locale("fr")
                ),
                showKeyboardOnFocus = true
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )

        OutlinedTextField(
            value = note.description,
            placeholder = {
                Text(text = "Description")
            },
            onValueChange = {
                onValueChange(note.copy(description = it))

            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}