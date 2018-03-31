package toggle.it.api.data

import toggle.it.api.domain.Toggle
import java.util.concurrent.atomic.AtomicInteger

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

object Toggles : Table(){
    val id =  integer("id").autoIncrement().primaryKey()
    val name = varchar("name", length = 50)
    val active = bool("active")
}

class ToggleRepositoryImpl : ToggleRepository {
    init {
        var connection = Database.connect("jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql")

        transaction {
            create (Toggles)
        }
    }

    private val toggles = hashMapOf(
            0 to Toggle(name = "toggle 1", active = true, id = 1),
            1 to Toggle(name = "toggle 2", active = false, id = 2)
    )

    var lastId: AtomicInteger = AtomicInteger(toggles.size)

    override fun getToggles(): List<Toggle> {
        return toggles.values.toList()
    }
}