package app.debugdesk.notebook.di

import android.app.Application
import android.content.Context
import app.debugdesk.notebook.utils.SharedObjects.createNotebookFolders
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module

class Notebook : Application() {
    override fun onCreate() {
        super.onCreate()
        initiateKoin(
            listOf(
                module {
                    single<Context> { this@Notebook }
                }
            )
        )

        createNotebookFolders()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}