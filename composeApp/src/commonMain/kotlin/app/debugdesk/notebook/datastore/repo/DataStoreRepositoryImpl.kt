package app.debugdesk.notebook.datastore.repo

import androidx.datastore.preferences.core.stringPreferencesKey
import app.debugdesk.notebook.datastore.NotebookDataStore

/**
 * Implements the [DataStoreRepository] interface to manage data stored within the application using DataStore.
 * It handles operations such as saving and retrieving posts, updating the application theme, and managing
 * the system palette preference.
 *
 * @param notebookDataStore The DataStore instance used for data storage.
 *
 * @constructor Creates an instance of [DataStoreRepositoryImpl] with the provided DataStore instance.
 *
 * @author Prashant Singh
 */
class DataStoreRepositoryImpl(private val notebookDataStore: NotebookDataStore) : DataStoreRepository {
    companion object {
        private val SAVED_POSTS = stringPreferencesKey("SAVED_POSTS")
        private val APP_THEME = stringPreferencesKey("APP_THEME")
        private val USE_SYSTEM_PALETTE = stringPreferencesKey("USE_SYSTEM_PALETTE")
    }
}
