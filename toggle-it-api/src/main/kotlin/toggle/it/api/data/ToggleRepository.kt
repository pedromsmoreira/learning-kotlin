package toggle.it.api.data

import toggle.it.api.domain.Toggle

interface ToggleRepository {
    fun getToggles() : List<Toggle>
}