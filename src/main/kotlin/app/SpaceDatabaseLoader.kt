package app

import app.di.localDataSourceModule
import app.di.repositoriesModule
import app.domain.model.Space
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

object SpaceDatabaseLoader : KoinComponent {

    suspend fun loadSpaceDatabase(space: Space) {
        unloadKoinModules(listOf(localDataSourceModule, repositoriesModule))
        val dbName = "${space.id}"
        Database.connect("jdbc:sqlite:$dbName.db", "org.sqlite.JDBC")
        newSuspendedTransaction { SchemaUtils.createDatabase("$dbName") }
        loadKoinModules(listOf(localDataSourceModule, repositoriesModule))
    }

    suspend fun closeSpaceDatabase() {
        unloadKoinModules(listOf(localDataSourceModule, repositoriesModule))
    }
}