package app.debugdesk.notebook.di

import app.debugdesk.notebook.utils.SharedObjects.createNotebookFolders


/**
 * [initKoin] function represents koin injection for iOS
 *
 * @author Prashant Singh
 * */
fun initKoin() {
    initiateKoin()
    createNotebookFolders()
}
