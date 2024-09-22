package app.debugdesk.notebook.presentations.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.debugdesk.notebook.domain.model.AppAppearance
import app.debugdesk.notebook.domain.repositories.AppStateOwner
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class NoteVM(
    private val appStateOwner: AppStateOwner
) : ViewModel(), KoinComponent {

    val appearance = appStateOwner.appAppearance

    fun updateAppearance(appAppearance: AppAppearance) {
        viewModelScope.launch {
            appStateOwner.setAppAppearance(appAppearance)
        }
    }

    init {
        viewModelScope.launch {
            appStateOwner.retrieveAppearance()
        }
    }

}