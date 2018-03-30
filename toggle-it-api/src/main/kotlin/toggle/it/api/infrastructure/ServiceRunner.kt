package toggle.it.api.infrastructure

import org.slf4j.LoggerFactory
import spark.Spark.port
import toggle.it.api.presentation.ToggleController

val toggleModule = applicationContext{

}

class ServiceRunner {


    companion object {
        private val logger = LoggerFactory.getLogger(ServiceRunner::class.java)

    }

    fun run(){
        port(7000)

        initControllers()
    }

    private fun initControllers() {
        ToggleController()
    }
}

