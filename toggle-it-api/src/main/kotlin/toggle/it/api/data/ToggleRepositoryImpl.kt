package toggle.it.api.data

import toggle.it.api.domain.Toggle
import java.util.concurrent.atomic.AtomicInteger

class ToggleRepositoryImpl : ToggleRepository {

    private val toggles = hashMapOf(
            0 to Toggle(name = "toggle 1", tags = listOf("1", "2"), active = true, id = 1),
            1 to Toggle(name = "toggle 2", tags = listOf("3", "4"), active = false, id = 2)
    )

    var lastId: AtomicInteger = AtomicInteger(toggles.size)

    override fun getToggles(): List<Toggle> {
        return toggles.values.toList()
    }
}