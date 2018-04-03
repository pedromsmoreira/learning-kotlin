package toggle.it.api.data

import toggle.it.api.domain.Toggle

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

object Toggles : Table(){
    val id =  integer("id").autoIncrement().primaryKey()
    val name = varchar("name", length = 50).uniqueIndex()
    val active = bool("active")
}

class ToggleRepositoryImpl : ToggleRepository {

    init {
        Database.connect("jdbc:postgresql://127.0.0.1:5432/postgres", driver = "org.postgresql.Driver", user = "postgres", password = "mysecretpassword")

        transaction {
            create (Toggles)
        }
    }

    override fun getToggles(): List<Toggle> {
        val toggles: MutableList<Toggle> =  mutableListOf()

        transaction {
            for(toggle in Toggles.selectAll()){
                toggles.add(Toggle(id = toggle[Toggles.id], name = toggle[Toggles.name], active = toggle[Toggles.active]))
            }
        }

        return toggles
    }

    override fun getToggleByName(name: String): Toggle {
        var toggle = Toggle()

        transaction {
            val result = Toggles.select {
                Toggles.name eq name
            }.first()

            toggle = Toggle(id = result[Toggles.id], name = result[Toggles.name], active = result[Toggles.active])
        }

        return toggle
    }

    override fun getToggleById(id: Int): Toggle {
        var toggle = Toggle()

        transaction {
            val result = Toggles.select {
                Toggles.id eq id
            }.first()

            toggle = Toggle(id = result[Toggles.id], name = result[Toggles.name], active = result[Toggles.active])
        }

        return toggle
    }

    override fun insert(toggle: Toggle) {
        transaction {
            Toggles.insert {
                it[Toggles.name] = toggle.name
                it[Toggles.active] = toggle.active
            }
        }
    }

    override fun delete(id: Int) {
        transaction {
            Toggles.deleteWhere {
                Toggles.id eq id
            }
        }
    }

    override fun update(id: Int) {
        transaction {

            val toggle = getToggleById(id)

            Toggles.update({
                Toggles.id.eq(id)
            })
            {
                it[Toggles.active] = !toggle.active
            }
        }
    }
}