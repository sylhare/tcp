package ktor

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import java.net.InetSocketAddress
import java.util.concurrent.Executors


suspend fun client() {
    val socket =
        aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress("127.0.0.1", 2323))
    val input = socket.openReadChannel()
    val output = socket.openWriteChannel(autoFlush = true)
    output.writeFully("hello\r\n".toByteArray())
    println("Server said: '${input.readUTF8Line()}'")
}

suspend fun server() {
    val server = aSocket(ActorSelectorManager(Executors.newCachedThreadPool().asCoroutineDispatcher())).tcp()
        .bind(InetSocketAddress("127.0.0.1", 2323))
    println("Server running: ${server.localAddress}")
    val socket = server.accept()
    println("Socket accepted: ${socket.remoteAddress}")
    val input = socket.openReadChannel()
    val output = socket.openWriteChannel(autoFlush = true)

    val line = input.readUTF8Line()
    println("received '$line' from ${socket.remoteAddress}")
    output.writeFully("$line\r\n back".toByteArray())
}

fun main() {
    CoroutineScope(Dispatchers.Default).launch { server() }
    runBlocking { client() }
}
