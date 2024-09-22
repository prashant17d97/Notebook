package app.debugdesk.notebook.utils.log

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLog
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSThread

/**
 * Logcat utility class for iOS platform.
 * Provides methods for logging different types of messages.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Logcat {
    /**
     * Companion object containing actual logging functions.
     */
    actual companion object {

        /**
         * Logs an error message with the specified tag and message.
         * @param tag The tag for identifying the source of the log message.
         * @param msg The message to be logged.
         */
        actual fun e(
            tag: String,
            msg: String,
        ) {
            NSLog("\uD83D\uDD34E   $tag        $msg")
        }

        /**
         * Logs a warning message with the specified tag and message.
         * @param tag The tag for identifying the source of the log message.
         * @param msg The message to be logged.
         */
        actual fun w(
            tag: String,
            msg: String,
        ) {
            NSLog("🟡 W   $tag        $msg")
        }

        /**
         * Logs a verbose message with the specified tag and message.
         * @param tag The tag for identifying the source of the log message.
         * @param msg The message to be logged.
         */
        actual fun v(
            tag: String,
            msg: String,
        ) {
            NSLog("\uD83D\uDFE3 V   $tag        $msg")
        }

        /**
         * Logs an informational message with the specified tag and message.
         * @param tag The tag for identifying the source of the log message.
         * @param msg The message to be logged.
         */
        actual fun i(
            tag: String,
            msg: String,
        ) {
            NSLog("\uD83D\uDFE2 I   $tag        $msg")
        }

        private fun logInfo(message: String) {
            val emoji = "🟡"
            val logLevel = "W"
            val appName = "NoteBookApp"

            // Getting Process ID
            val processId = NSProcessInfo.processInfo.processIdentifier

            // Getting Thread Info
            val thread = NSThread.currentThread.name
            // Formatting date and time
            val timestamp = formatCurrentDate()

            NSLog("$emoji $logLevel   $appName        $message [ProcessId:$processId] [Thread:$thread] at $timestamp")
        }

        private fun formatCurrentDate(): String {
            val dateFormatter = NSDateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss.SSSSSSZZZZZ"
            return dateFormatter.stringFromDate(NSDate())
        }
        


        /**
         * Logs a debug message with the specified tag and message.
         * @param tag The tag for identifying the source of the log message.
         * @param msg The message to be logged.
         */
        actual fun d(
            tag: String,
            msg: String,
        ) {
            NSLog("\uD83D\uDD35D   $tag        $msg")
        }
    }
}
