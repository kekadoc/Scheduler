package trash/*
object Users : IntIdTable() {
    val name = varchar("name", 50).index()
    val city = reference("city", Cities)
    val age = integer("age")
}

object Cities: IntIdTable() {
    val name = varchar("name", 50)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var city by City referencedOn Users.city
    var age by Users.age
}

class City(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<City>(Cities)

    var name by Cities.name
    val users by User referrersOn Users.city
}

fun main() {

    //Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
    Database.connect("jdbc:sqlite:app.data.db", "org.sqlite.JDBC")

    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    // or Connection.TRANSACTION_READ_UNCOMMITTED
    transaction {
        addLogger(StdOutSqlLogger)
        //SchemaUtils.createDatabase("app.data.db")
        SchemaUtils.create (Cities, Users)

        val stPete = City.new {
            name = "St. Petersburg"
        }

        val munich = City.new {
            name = "Munich"
        }

        User.new {
            name = "a"
            city = stPete
            age = 5
        }

        User.new {
            name = "b"
            city = stPete
            age = 27
        }

        User.new {
            name = "c"
            city = munich
            age = 42
        }

        println("Cities: ${City.all().joinToString {it.name}}")
        println("Users in ${stPete.name}: ${stPete.users.joinToString {it.name}}")
        println("Adults: ${User.find { Users.age greaterEq 18 }.joinToString {it.name}}")
    }
}*/

/*    Database.connect("jdbc:sqlite:app.data.db", "org.sqlite.JDBC")

    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    val teacherLocalDataSource = TeacherLocalDataSourceImpl()
    teacherLocalDataSource.app.data.onEach {
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