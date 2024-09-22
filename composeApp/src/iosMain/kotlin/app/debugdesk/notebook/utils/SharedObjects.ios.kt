package app.debugdesk.notebook.utils

import androidx.compose.ui.graphics.Color
import app.debugdesk.notebook.data.util.CommonObjects.AUDIO
import app.debugdesk.notebook.data.util.CommonObjects.DOCUMENTS
import app.debugdesk.notebook.data.util.CommonObjects.NOTEBOOK
import app.debugdesk.notebook.data.util.CommonObjects.PICTURE
import app.debugdesk.notebook.data.util.CommonObjects.VIDEO
import app.debugdesk.notebook.utils.log.Logcat
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocPointerTo
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import platform.Foundation.NSData
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.dataWithBytes
import platform.Foundation.systemTimeZone
import platform.Foundation.writeToFile
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIFont.Companion.systemFontOfSize
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.UIKit.UIViewAnimationOptionCurveEaseOut

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object SharedObjects : KoinComponent {

    actual val isAndroid: Boolean = false

    @OptIn(ExperimentalForeignApi::class)
    actual fun createNotebookFolders() {

        try {
            val fileManager = NSFileManager.defaultManager
            val baseDir = NSSearchPathForDirectoriesInDomains(
                NSDocumentDirectory,
                NSUserDomainMask,
                true
            ).first() as String
            val notebookDir = "$baseDir/$NOTEBOOK"

            val pictureDir = "$notebookDir/$PICTURE"
            val videoDir = "$notebookDir/$VIDEO"
            val audioDir = "$notebookDir/$AUDIO"
            val documentDir = "$notebookDir/$DOCUMENTS"


            // Prepare an error pointer
            memScoped {
                val error = allocPointerTo<ObjCObjectVar<NSError?>>()

                // Create directories with error handling
                fileManager.createDirectoryAtPath(
                    path = pictureDir,
                    withIntermediateDirectories = true,
                    attributes = null,
                    error = error.value
                )
                fileManager.createDirectoryAtPath(
                    videoDir,
                    withIntermediateDirectories = true,
                    attributes = null,
                    error = error.value
                )
                fileManager.createDirectoryAtPath(
                    audioDir,
                    withIntermediateDirectories = true,
                    attributes = null,
                    error = error.value
                )
                fileManager.createDirectoryAtPath(
                    documentDir,
                    withIntermediateDirectories = true,
                    attributes = null,
                    error = error.value
                )

                // Check if an error occurred
                error.value?.let { err ->
                    Logcat.e("SharedObjects", "Error creating directories: $err")
                    println("Error creating directories: $err")
                }
            }
        } catch (exception: Exception) {
            Logcat.e("SharedObjects", "createNotebookFolders: ${exception.message.toString()}")
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun saveFileToFolder(folderName: String, fileName: String, data: ByteArray): Boolean {
        val fileManager = NSFileManager.defaultManager
        val baseDir = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String
        val folderPath = "$baseDir/$NOTEBOOK/$folderName"
        val filePath = "$folderPath/$fileName"

        // Ensure the folder exists
        if (!fileManager.fileExistsAtPath(folderPath)) {
            memScoped {
                val errorPtr = allocPointerTo<ObjCObjectVar<NSError?>>()

                val success = fileManager.createDirectoryAtPath(
                    path = folderPath,
                    withIntermediateDirectories = true,
                    attributes = null,
                    error = errorPtr.value
                )

                // Handle the case where directory creation fails
                if (!success) {
                    errorPtr.value?.let { error ->
                        println("Error creating directory: ${error}")
                        return false // Exit early if directory creation fails
                    }
                }
            }
        }

        // Write the file
        return data.usePinned { pinnedData ->
            val dataNSData = NSData.dataWithBytes(pinnedData.addressOf(0), data.size.toULong())
            dataNSData.writeToFile(filePath, true) // Returns true if file writing is successful
        }
    }

    actual fun toastMsg(message: String) {
        val toastLabel =
            UILabel().apply {
                backgroundColor = Color.Black.copy(alpha = 0.6f).getColor()
                textColor = Color.White.getColor()
                textAlignment = NSTextAlignmentCenter
                font = systemFontOfSize(15.0)
                text = message
                alpha = 1.0
                layer.cornerRadius = 10.0
                clipsToBounds = true
                UIApplication.sharedApplication.keyWindow?.addSubview(this)
            }

        UIView.animateWithDuration(
            duration = 2.0,
            delay = 0.1,
            options = UIViewAnimationOptionCurveEaseOut,
            animations = {
                toastLabel.alpha = 0.0
            },
            completion = { _ ->
                toastLabel.removeFromSuperview()
            },
        )
    }

    private fun Color.getColor(): UIColor {
        return UIColor(
            red = red.toDouble(),
            green = green.toDouble(),
            blue = blue.toDouble(),
            alpha = alpha.toDouble(),
        )
    }

    actual fun Long.toFormattedDate(format: String): String {
        val date = NSDate(timeIntervalSinceReferenceDate = this.toDouble())
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            timeZone = platform.Foundation.NSTimeZone.systemTimeZone
        }
        return formatter.stringFromDate(date)
    }

    actual fun Long.toFormattedCurrentDate(format: String): String {
        val timeInMilliseconds = if (this > 0L) this else Clock.System.now().epochSeconds
        val date = NSDate(timeIntervalSinceReferenceDate = this.toDouble())
        val formatter = NSDateFormatter().apply {
            dateFormat = format
            timeZone = platform.Foundation.NSTimeZone.systemTimeZone
        }
        return formatter.stringFromDate(date)
    }
}