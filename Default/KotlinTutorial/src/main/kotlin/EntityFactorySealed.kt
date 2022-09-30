import java.util.*

enum class EntityType2 {
    HELP, EASY, MEDIUM, HARD;

    fun getFormattedName() = name.lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

object EntityFactory3 {
    fun create(type: EntityType2) : Entity3 {
        val id = UUID.randomUUID().toString()
        val name = when(type) {
            EntityType2.EASY -> type.name
            EntityType2.MEDIUM -> type.getFormattedName()
            EntityType2.HARD -> "Hard"
            EntityType2.HELP -> type.getFormattedName()
        }

        return when(type) {
            EntityType2.EASY -> Entity3.Easy(id, name)
            EntityType2.MEDIUM -> Entity3.Medium(id, name)
            EntityType2.HARD -> Entity3.Hard(id, name, 2f)
            EntityType2.HELP -> Entity3.Help
        }
    }
}

sealed class Entity3 () {
    object Help : Entity3() {
        val name = "Help"
    }
    data class Easy(val id: String, val name: String) : Entity3()
    data class Medium(val id: String, val name: String) : Entity3()
    data class Hard(val id: String, val name: String, val multiplier: Float) : Entity3()
}

fun main() {
    val entity: Entity3 = EntityFactory3.create(EntityType2.EASY)
    val msg = when(entity) {
        is Entity3.Easy -> "Easy class"
        is Entity3.Hard ->  "Hard class"
        Entity3.Help -> "Help class"
        is Entity3.Medium ->  "Medium class"
    }

    println(msg)

    println("====")
    val entity1 = Entity3.Easy("id", "name")
    val entity2 = Entity3.Easy("id", "name")

    if (entity1 === entity2) {
        println("they are equal")
    } else {
        println("they are not equal")
    }
}
