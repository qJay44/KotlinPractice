fun main() {
    val list = listOf("Kotlin", "Java", "Javascript", null, null)
    val map = list
        .filterNotNull()
        .associate { it to it.length }

    val language = list.filterNotNull().findLast { it.startsWith("Java") }.orEmpty()
    println(language)
}