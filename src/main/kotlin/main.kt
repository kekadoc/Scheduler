
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.Application
import di.dataConverterModule
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
            dataConverterModule,
            viewModelsModule
        )
    }
    val application = Application()
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(size = DpSize(1280.dp, 720.dp))
    ) {
        //TestUi()
         application.onCreate()
    }
}
