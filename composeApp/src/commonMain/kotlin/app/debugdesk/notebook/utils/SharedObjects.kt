package app.debugdesk.notebook.utils

import org.koin.core.component.KoinComponent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object SharedObjects : KoinComponent {
    val isAndroid: Boolean
    fun createNotebookFolders()

    fun saveFileToFolder(folderName: String, fileName: String, data: ByteArray): Boolean


    /**
     * Declares an expected composable function for displaying toast messages, providing platform-specific
     * implementations for iOS and Android.
     *
     * On Android, this function will show a toast message using the native Toast API.
     * On iOS, the implementation will utilize platform-specific APIs for displaying toast-like messages.
     *
     * @param message The message to be displayed in the toast.
     */
    fun toastMsg(message: String)

}
