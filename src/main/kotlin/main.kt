import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.Application
import di.localDataSourceModule
import di.repositoriesModule
import di.viewModelsModule
import org.jetbrains.exposed.sql.Database
import org.koin.core.context.startKoin

fun main() = application {
    Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
    startKoin {
        printLogger() 
        modules(
            localDataSourceModule,
            repositoriesModule,
            viewModelsModule
        )
    }
    val application = Application()
    Window(onCloseRequest = ::exitApplication) {
        application.onCreate()
    }
}
