package com.yunchi

import com.yunchi.core.goods_system.itemSystem
import com.yunchi.core.user_system.userSystem
import com.yunchi.plugins.configurePing
import com.yunchi.plugins.configureSecurity
import com.yunchi.plugins.configureSockets
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty,
        Config.server.port,
        Config.server.baseUrl){
        mainModule()
    }.start(true)
}

@Suppress("unused")
fun Application.mainModule() {
    configureSockets()
    configureSecurity()
    configurePing()
    userSystem()
    itemSystem()
}
