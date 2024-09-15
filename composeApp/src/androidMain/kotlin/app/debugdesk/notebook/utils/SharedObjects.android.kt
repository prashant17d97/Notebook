package app.debugdesk.notebook.utils

import android.content.Context
import android.widget.Toast
import app.debugdesk.notebook.data.util.CommonObjects.AUDIO
import app.debugdesk.notebook.data.util.CommonObjects.DOCUMENTS
import app.debugdesk.notebook.data.util.CommonObjects.NOTEBOOK
import app.debugdesk.notebook.data.util.CommonObjects.PICTURE
import app.debugdesk.notebook.data.util.CommonObjects.VIDEO
import app.debugdesk.notebook.utils.log.Logcat
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileOutputStream

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object SharedObjects : KoinComponent {

    actual val isAndroid: Boolean = true

    private val context: Context by inject()

    actual fun createNotebookFolders() {
        val baseDir = context.getExternalFilesDir(null)?.let { File(it, NOTEBOOK) }

        if (baseDir != null) {
            val pictureDir = File(baseDir, PICTURE)
            val videoDir = File(baseDir, VIDEO)
            val audioDir = File(baseDir, AUDIO)
            val documentDir = File(baseDir, DOCUMENTS)

            // Create the directories
            pictureDir.mkdirs()
            videoDir.mkdirs()
            audioDir.mkdirs()
            documentDir.mkdirs()
        }
    }


    actual fun saveFileToFolder(folderName: String, fileName: String, data: ByteArray): Boolean {
        // Get the app-specific external storage directory
        val baseDir = context.getExternalFilesDir(null)?.let { File(it, "$NOTEBOOK/$folderName") }

        // Ensure the folder exists or create it if it doesn't
        if (baseDir != null && !baseDir.exists()) {
            val folderCreated = baseDir.mkdirs()
            if (!folderCreated) {
                Logcat.e("SharedObjects", "saveFileToFolder-ErrorAtFolderCreation: Failed to create folder $folderName")
                return false
            }
        }

        // Proceed to save the file in the specific folder
        return if (baseDir != null && baseDir.exists()) {
            val file = File(baseDir, fileName)
            try {
                val outputStream = FileOutputStream(file)
                outputStream.write(data)
                outputStream.close()
                true
            } catch (e: Exception) {
                Logcat.e("SharedObjects", "saveFileToFolder-ErrorInSaving: ${e.message.toString()}")
                e.printStackTrace()
                false
            }
        } else {
            false
        }
    }
    /**
     * Composable function for displaying a toast message.
     * This function displays a toast message with the specified [message].
     *
     * @param message The message to be displayed in the toast.
     *
     * @author Prashant Singh
     */
    actual fun toastMsg(message: String) {        // Show the toast message
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}