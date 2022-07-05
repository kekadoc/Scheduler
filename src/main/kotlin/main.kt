
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.Application
import app.di.dataConverterModule
import app.di.dataInjectorModule
import app.di.spacesModule
import app.di.viewModelsModule
import org.koin.core.context.startKoin

fun main() = application {
    //Database.connect("jdbc:sqlite:app.data.db", "org.sqlite.JDBC")

    /*GlobalScope.launch {
        newSuspendedTransaction { SchemaUtils.createDatabase() }
    }*/
    startKoin {
        printLogger() 
        modules(
            spacesModule,
            dataConverterModule,
            viewModelsModule,
            dataInjectorModule,
            //localDataSourceModule,
            //repositoriesModule,
        )
    }

    val application = Application()
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(size = DpSize(1280.dp, 720.dp)),
        resizable = false
    ) {
         application.onCreate()
    }
}
