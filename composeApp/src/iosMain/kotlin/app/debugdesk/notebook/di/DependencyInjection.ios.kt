package app.debugdesk.notebook.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.debugdesk.notebook.datastore.dataStore
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module


/**
 * Declares an expect property platformModule to be implemented in platform-specific code.
 * This module provides platform-specific dependencies required for the application.
 *
 * @see Module
 *
 * @property platformModule The platform-specific Koin module.
 *
 * @constructor Creates an instance of [platformModule].
 *
 * @author Prashant Singh
 */
actual val platformModule = module {
    single(named("Platform")) { "iOS" }
    // Provides a DataStore instance for managing preferences on the iOS side
    single<DataStore<Preferences>> { dataStore() }
}