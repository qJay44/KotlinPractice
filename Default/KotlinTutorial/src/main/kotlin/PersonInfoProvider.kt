interface PersonInfoProvider {
    val providerInfo: String

    fun printInfo(person: Person) {
        println(providerInfo)
        person.printInfo()
    }
}


interface SessionInfoProvider {
    fun getSessionId() : String
}


class BasicInfoProvider : PersonInfoProvider, SessionInfoProvider {
    override val providerInfo: String
        get() = "basicInfoProvider"

    override fun printInfo(person: Person) {
        super.printInfo(person)
        println("additional info")

    }

    override fun getSessionId(): String {
        return "Session"
    }
}


fun main() {
    val provider = BasicInfoProvider()
    provider.printInfo(Person())
    provider.getSessionId()

    checkTypes(provider)
}

fun checkTypes(infoProvider: PersonInfoProvider) {
    if (infoProvider !is SessionInfoProvider) {
        println("not a session info provider")
    } else {
        println("is a session info provider")
        infoProvider.getSessionId()
    }

}
