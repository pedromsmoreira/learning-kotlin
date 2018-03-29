package api.kotlin.presentation

import api.kotlin.data.UserRepository
import api.kotlin.domain.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import com.fasterxml.jackson.module.kotlin.registerKotlinModule

import io.javalin.Javalin
import io.javalin.ApiBuilder.*

fun main(args: Array<String>) {

    val userRepository = UserRepository()
    val mapper = ObjectMapper().registerKotlinModule()

    val app = Javalin
            .create()
            .apply {
                port(7000)
                exception(Exception::class.java){ ex, ctx -> ex.printStackTrace() }
                error(404) { ctx -> ctx.json("not found") }
            }
            .start()

    app.routes {

        get("/users"){
            ctx -> ctx.json(userRepository.users)
        }

        get("/users/:id"){
            ctx -> ctx.json(userRepository.getById(ctx.param("id")!!.toInt())!!)
        }

        get("/users/:email"){
            ctx -> ctx.json(userRepository.getByEmail(ctx.param("email")!!)!!)
        }

        post("/users"){
            ctx ->
            val user = mapper.readValue<User>(ctx.body())

            userRepository.save(user.name, user.email)

            ctx.status(201)
        }


    }
}
