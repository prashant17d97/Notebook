package app.debugdesk.notebook.stateowners.appstatesowner

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppStateOwnerImpl : AppStateOwner {

    private val _showFab = MutableStateFlow(true)

    private val _showAllCheckedBox = MutableStateFlow(false)
    override val showAllCheckedBox: StateFlow<Boolean>
        get() = _showAllCheckedBox


    override val showFab: StateFlow<Boolean> = _showFab//.asStateFlow()

    override fun setShowFab(show: Boolean) {
        _showFab.value = show
    }

    override fun setEnableAllCheckBox() {
        _showAllCheckedBox.value = !showAllCheckedBox.value
    }

}