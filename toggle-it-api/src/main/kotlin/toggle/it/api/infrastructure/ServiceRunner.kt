package toggle.it.api.infrastructure

import org.koin.dsl.module.applicationContext

import org.koin.spark.controller
import org.koin.spark.runControllers
import org.koin.spark.start

import org.slf4j.LoggerFactory
import spark.Spark.port

import toggle.it.api.data.ToggleRepository
import toggle.it.api.data.ToggleRepositoryImpl
import toggle.it.api.presentation.ToggleController

val toggleModule = applicationContext{
    bean { ToggleRepositoryImpl()  as ToggleRepository }
    controller { ToggleController(get()) }
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
        start(modules = listOf(toggleModule)){
            runControllers()
        }
    }
}

