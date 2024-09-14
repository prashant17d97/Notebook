package app.debugdesk.notebook.stateowners.appstatesowner

import app.debugdesk.notebook.datamodel.AppAppearance
import kotlinx.coroutines.flow.StateFlow

interface AppStateOwner {
    val showAllCheckedBox: StateFlow<Boolean>
    val showFab: StateFlow<Boolean>
    val appAppearance: StateFlow<AppAppearance>

    fun setEnableAllCheckBox()
    fun setShowFab(show: Boolean)

    suspend fun setAppAppearance(appAppearance: AppAppearance)

    suspend fun setTempAppearance(appAppearance: AppAppearance)

    suspend fun retrieveAppearance()
}