package app.debugdesk.notebook.utils

import org.koin.core.component.KoinComponent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object SharedObjects : KoinComponent {

    fun createNotebookFolders()

    fun saveFileToFolder(folderName: String, fileName: String, data: ByteArray): Boolean
}
