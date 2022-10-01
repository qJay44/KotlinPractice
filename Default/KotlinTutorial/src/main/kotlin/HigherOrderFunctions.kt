fun printFilteredStrings(list: List<String>, predicate: ((String) -> Boolean)?) {
    list.forEach {
        if (predicate?.invoke(it) == true) {
            println(it)
        }
    }
}

val predicate: (String) -> Boolean = {
    it.startsWith("K")
}

fun getPrintPredicate(): (String) -> Boolean {
    return { it.startsWith("K")}
}

fun main() {
    val list = listOf("a", "b", "c")
    printFilteredStrings(list, getPrintPredicate())

    printFilteredStrings(list, null)
}