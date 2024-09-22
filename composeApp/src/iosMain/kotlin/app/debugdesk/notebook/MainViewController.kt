package app.debugdesk.notebook

import androidx.compose.ui.window.ComposeUIViewController
import app.debugdesk.notebook.presentations.app.NoteBookApp

fun MainViewController() = ComposeUIViewController { NoteBookApp() }