package app.debugdesk.notebook.di

import androidx.lifecycle.ViewModel
import app.debugdesk.database.NotebookDatabase
import app.debugdesk.notebook.data.local.NoteDataBaseFactory
import app.debugdesk.notebook.data.local.NotebookDataStore
import app.debugdesk.notebook.data.repository.AppStateOwnerImpl
import app.debugdesk.notebook.data.repository.DataStoreRepositoryImpl
import app.debugdesk.notebook.data.repository.NoteRepositoryImpl
import app.debugdesk.notebook.di.platform.platformModule
import app.debugdesk.notebook.domain.repositories.AppStateOwner
import app.debugdesk.notebook.domain.repositories.DataStoreRepository
import app.debugdesk.notebook.domain.repositories.NoteRepository
import app.debugdesk.notebook.presentations.app.NoteVM
import app.debugdesk.notebook.presentations.home.HomeViewModel
import app.debugdesk.notebook.presentations.note.NoteViewModel
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
    single<NotebookDatabase> {
        NotebookDatabase(driver = NoteDataBaseFactory.create())
    }
    single<NoteRepository> { NoteRepositoryImpl(database = get()) }

    single<AppStateOwner> { AppStateOwnerImpl(dataStoreRepository = get()) }

    viewModel { HomeViewModel() }
    viewModel { NoteVM(appStateOwner = get()) }
    viewModel { NoteViewModel() }
}

private inline fun <reified T : ViewModel> Module.viewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition)
}