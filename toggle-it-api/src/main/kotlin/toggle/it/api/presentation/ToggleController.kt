package toggle.it.api.presentation

import spark.Route
import spark.Spark.*
import toggle.it.api.data.ToggleRepository
import toggle.it.api.infrastructure.transformers.JsonResponseTransformer

class ToggleController(val repository: ToggleRepository) {

    init {
        initRoutes()
    }

    private fun initRoutes(){
        get("/toggles", getAllToggles(), JsonResponseTransformer())
        get("/toggles/:id", { req, res -> "get toggle by id"})
        get("/toggles/:id/tags", { req, res -> "get tags from toggle"})

        post("/toggles", { req, res -> "create toggle" })
        patch("/toggles/:id/switch", { req, res -> "activate or deactivate toggle" })
        delete("/toggles/:id", {req, res -> "delete toggle" })
    }

    private fun getAllToggles() = Route {
        req, res -> repository.getToggles()
    }
}