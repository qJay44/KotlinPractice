class Person(val firstName: String = "Peter", val lastName: String = "Parker") {
    var nickName: String? = null
        set(value) {
            field = value
            println("new nn is $value")
        }
        get() {
            println("return field")
            return field
        }

    fun printInfo() {
        val nickNameTopPrint = nickName ?: "no nickname"
        println("$firstName ($nickNameTopPrint) $lastName")
    }
}
