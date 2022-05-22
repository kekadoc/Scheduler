import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.Application
import di.localDataSourceModule
import di.repositoriesModule
import di.viewModelsModule
import org.koin.core.context.startKoin

fun main() = application {

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
/*    Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")

    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    val teacherLocalDataSource = TeacherLocalDataSourceImpl()
    teacherLocalDataSource.data.onEach {
        println("Teachers: $it")
    }.launchIn(GlobalScope)

    GlobalScope.launch {
        delay(200)
        val teacher = teacherLocalDataSource.create {
            firstName = "Иван"
            lastName = "Иванов"
            middleName = "Иванович"
        }.apply {
            println("Adding: $this")
        }
        delay(200)
        teacher.onSuccess {
            teacherLocalDataSource.read(it.id).apply {
                println("Getting: $this")
            }
            delay(200)
            teacherLocalDataSource.delete(it.id).apply {
                println("Deleting: $this")
            }
        }
    }*/
}
