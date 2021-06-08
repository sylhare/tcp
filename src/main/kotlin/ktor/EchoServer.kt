package ktor

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress
import java.util.concurrent.Executors


internal class EchoServer {

    fun start() {
        runBlocking {
            val server = aSocket(ActorSelectorManager(Executors.newCachedThreadPool().asCoroutineDispatcher())).tcp()
                .bind(InetSocketAddress("127.0.0.1", 2224))
            println("Started echo telnet server at ${server.localAddress}")

            while (true) {
                val socket = server.accept()

                launch {
                    println("Socket accepted: ${socket.remoteAddress}")

                    val input = socket.openReadChannel()
                    val output = socket.openWriteChannel(autoFlush = true)

                    try {
                        while (true) {
                            val line = input.readUTF8Line()

                            println("${socket.remoteAddress}: $line")
                            output.writeFully("$line\r\n".toByteArray())
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        socket.close()
                    }
                }
            }
        }
    }
}

/**
 * https://ktor.io/servers/raw-sockets.html
 * telnet 127.0.0.1 2224
 */

fun main() {
    EchoServer().start()
}


