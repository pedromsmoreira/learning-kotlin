package toggle.it.api.infrastructure.transformers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import spark.ResponseTransformer

class JsonResponseTransformer : ResponseTransformer {

    private val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

    override fun render(model: Any?) = gson.toJson(model)
}