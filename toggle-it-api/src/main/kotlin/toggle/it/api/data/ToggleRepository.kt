package toggle.it.api.data

import toggle.it.api.domain.Toggle

interface ToggleRepository {
    fun getToggles() : List<Toggle>

    fun getToggleById(id: Int) : Toggle?

    fun getToggleByName(name: String) : Toggle?

    fun insert(toggle: Toggle)

    fun update(id: Int)

    fun delete(id: Int)
}