package app.debugdesk.notebook.stateowners.appstatesowner

import kotlinx.coroutines.flow.StateFlow

interface AppStateOwner {
    val showAllCheckedBox: StateFlow<Boolean>
    val showFab: StateFlow<Boolean>

    fun setEnableAllCheckBox()
    fun setShowFab(show: Boolean)
}