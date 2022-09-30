import java.util.*

enum class EntityType {
    EASY, MEDIUM, HARD;

    fun getFormattedName() = name.lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

object EntityFactory2 {
    fun create(type: EntityType) : Entity2 {
        val id = UUID.randomUUID().toString()
        val name = when(type) {
            EntityType.EASY -> type.name
            EntityType.MEDIUM -> type.getFormattedName()
            EntityType.HARD -> "Hard"
        }

        return Entity2(id, name)
    }
}

class Entity2 (val id: String, val name: String) {
    override fun toString(): String {
        return "id:$id name:$name"
    }
}

fun main() {
    val entity = EntityFactory2.create(EntityType.EASY)
    println(entity)

    val mediumEntity = EntityFactory2.create(EntityType.MEDIUM)
    println(mediumEntity)
}
