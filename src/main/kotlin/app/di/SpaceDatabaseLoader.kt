package app.di

import app.domain.model.Space
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.io.File
import java.sql.Connection

object SpaceDatabaseLoader : KoinComponent {

    suspend fun loadSpaceDatabase(space: Space) {
        unloadKoinModules(listOf(localDataSourceModule, repositoriesModule))
        val dbName = "${space.id}"

        val dataFolderName = "./data"
        val dataFolder = File(dataFolderName)
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }
        Database.connect(url = "jdbc:sqlite:$dataFolderName/$dbName.db", driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_READ_UNCOMMITTED

        //newSuspendedTransaction { SchemaUtils.createDatabase("$dataFolderName/$dbName") }
        loadKoinModules(listOf(localDataSourceModule, repositoriesModule))
    }

    suspend fun closeSpaceDatabase() {
        unloadKoinModules(listOf(localDataSourceModule, repositoriesModule))
    }
}