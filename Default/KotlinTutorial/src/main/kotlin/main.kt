fun sayHello(greeting: String, vararg items: String) {
    items.forEach { Item ->
        println("$greeting $Item")
    }
}

fun greetPerson(greeting: String = "Hello", name: String) = println("$greeting $name")

fun main() {
    val interestingThings = arrayOf("a", "b", "c")
    println("====")
    sayHello("hi", "a", "b", "c")
    sayHello("hi", *interestingThings)

    println("====")
    greetPerson(greeting="hi", name = "Nate")
    greetPerson(name = "Nate")

    println("====")
    sayHello(greeting = "Hi", items = *interestingThings)

    println("====")
    val person = Person()
    person.lastName
    person.firstName
    person.nickName = "shades"
    println(person.nickName)

    println("====")
    person.printInfo()
}