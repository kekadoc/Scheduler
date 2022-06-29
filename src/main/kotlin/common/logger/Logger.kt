package common.logger

interface Logger {

    fun log(message: String)

    fun log(message: String, throwable: Throwable)

    companion object : Logger {

        override fun log(message: String) {
            println(message)
        }

        override fun log(message: String, throwable: Throwable) {
            println(message)
            throwable.printStackTrace()
        }

    }
}