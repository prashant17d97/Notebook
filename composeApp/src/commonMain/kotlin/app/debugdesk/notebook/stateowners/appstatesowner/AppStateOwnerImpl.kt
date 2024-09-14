package app.debugdesk.notebook.stateowners.appstatesowner

import app.debugdesk.notebook.datamodel.AppAppearance
import app.debugdesk.notebook.datastore.repo.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppStateOwnerImpl(private val dataStoreRepository: DataStoreRepository) : AppStateOwner {

    private val _showFab = MutableStateFlow(true)
    override val showFab: StateFlow<Boolean> = _showFab

    private val _showAllCheckedBox = MutableStateFlow(false)
    override val showAllCheckedBox: StateFlow<Boolean>
        get() = _showAllCheckedBox

    private val _appAppearance = MutableStateFlow(AppAppearance())
    override val appAppearance: StateFlow<AppAppearance>
        get() = _appAppearance


    override fun setShowFab(show: Boolean) {
        _showFab.value = show
    }

    override fun setEnableAllCheckBox() {
        _showAllCheckedBox.value = !showAllCheckedBox.value
    }

    override suspend fun setAppAppearance(appAppearance: AppAppearance) {
        _appAppearance.tryEmit(appAppearance)
        dataStoreRepository.saveAppAppearance(appAppearance)
    }

    override suspend fun setTempAppearance(appAppearance: AppAppearance) {
        _appAppearance.tryEmit(appAppearance)
    }

    override suspend fun retrieveAppearance() {
        _appAppearance.tryEmit(dataStoreRepository.fetchAppearance())
    }
}