package app.debugdesk.notebook.data.local

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.component.KoinComponent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object NoteDataBaseFactory : KoinComponent {
    fun create(): SqlDriver
}