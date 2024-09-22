package app.debugdesk.notebook.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import app.debugdesk.notebook.data.local.NotebookDataStore
import app.debugdesk.notebook.data.util.DataParser.toData
import app.debugdesk.notebook.data.util.DataParser.toJson
import app.debugdesk.notebook.domain.model.AppAppearance
import app.debugdesk.notebook.domain.repositories.DataStoreRepository
import app.debugdesk.notebook.utils.log.Logcat
import kotlinx.coroutines.flow.firstOrNull

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
class DataStoreRepositoryImpl(private val notebookDataStore: NotebookDataStore) :
    DataStoreRepository {

    override suspend fun saveAppAppearance(appAppearance: AppAppearance) {
        notebookDataStore.set(APP_APPEARANCE, appAppearance.toData())
    }

    override suspend fun fetchAppearance(): AppAppearance {
        val appAppearanceString = notebookDataStore.getString(APP_APPEARANCE).firstOrNull()
        return appAppearanceString?.let {
            try {
                it.toJson<AppAppearance>()
            } catch (e: Exception) {
                Logcat.e("DataStoreRepositoryImpl", e.message.toString())
                AppAppearance()
            }
        } ?: AppAppearance()
    }

    companion object {
        private val APP_APPEARANCE = stringPreferencesKey("APP_APPEARANCE")
    }
}
