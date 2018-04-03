package toggle.it.api.presentation

import com.google.gson.Gson
import spark.Route
import spark.Spark.*
import toggle.it.api.data.ToggleRepository
import toggle.it.api.domain.Toggle
import toggle.it.api.infrastructure.transformers.JsonResponseTransformer
import toggle.it.api.presentation.dto.CreateToggleCommand
import toggle.it.api.presentation.dto.ErrorMessage

class ToggleController(val repository: ToggleRepository) {

    val gson = Gson()
    init {
        initRoutes()
    }

    private fun initRoutes(){
        get("/toggles", getAllToggles(), JsonResponseTransformer())
        get("/toggles/:id", getToggleById(), JsonResponseTransformer())
        get("/toggles/:name", getToggleByName(), JsonResponseTransformer())

        post("/toggles", createToggle(), JsonResponseTransformer())
        put("/toggles/:id/switch", switchToggle(), JsonResponseTransformer())
        delete("/toggles/:id", deleteToggle(), JsonResponseTransformer())
    }

    private fun getAllToggles() = Route {
        _, _ -> repository.getToggles()
    }

    private fun getToggleById() = Route {
        req, _ -> repository.getToggleById(req.params(":id").toInt()) ?: notFound("Toggle with id: ${req.params(":id")} does not exist.")
    }

    private fun getToggleByName() = Route {
        req, _ -> repository.getToggleByName(req.params(":name")) ?: notFound("Toggle with name: ${req.params(":name")} does not exist.")
    }

    private fun switchToggle() = Route {
        req, _ -> repository.update(req.params(":id").toInt())
    }

    private fun deleteToggle() = Route {
        req, _ -> repository.delete(req.params(":id").toInt())
    }

    private fun createToggle() = Route {
        req, res ->
        val createCommand = gson.fromJson(req.body(), CreateToggleCommand::class.java)

        try {
            repository.insert(Toggle(createCommand.name, createCommand.active))
            res.status(202)
        }
        catch (alreadyExists: Exception){
            res.status(500)
            ErrorMessage(message = "An Error has occurred. Check stacktrace", stackTrace = alreadyExists.toString())
        }
    }
}



