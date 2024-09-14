package app.debugdesk.notebook.di

import androidx.lifecycle.ViewModel
import app.debugdesk.notebook.NoteVM
import app.debugdesk.notebook.datastore.NotebookDataStore
import app.debugdesk.notebook.datastore.repo.DataStoreRepository
import app.debugdesk.notebook.datastore.repo.DataStoreRepositoryImpl
import app.debugdesk.notebook.presentations.home.HomeViewModel
import app.debugdesk.notebook.presentations.note.NoteViewModel
import app.debugdesk.notebook.repositories.implementation.NoteRepositoryImpl
import app.debugdesk.notebook.repositories.interfaces.NoteRepository
import app.debugdesk.notebook.stateowners.appstatesowner.AppStateOwner
import app.debugdesk.notebook.stateowners.appstatesowner.AppStateOwnerImpl
import org.koin.core.context.startKoin
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

fun initiateKoin(additionalModules: List<Module>? = null) {
    startKoin {
        modules(additionalModules.orEmpty() + listOf(commonModule, platformModule))
    }
}

private val commonModule = module {
    single<NotebookDataStore> {
        NotebookDataStore(dataStore = get())
    }
    single<DataStoreRepository> {
        DataStoreRepositoryImpl(notebookDataStore = get())
    }
    single<NoteRepository> { NoteRepositoryImpl() }

    single<AppStateOwner> { AppStateOwnerImpl(dataStoreRepository = get()) }

    viewModel { HomeViewModel() }
    viewModel { NoteVM(appStateOwner = get()) }
    viewModel { NoteViewModel() }


}


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
expect val platformModule: Module


private inline fun <reified T : ViewModel> Module.viewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition)
}