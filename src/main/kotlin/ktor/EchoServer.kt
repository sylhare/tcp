package ktor

import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.cio.write
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress
import java.util.concurrent.Executors


class EchoServer {

    @KtorExperimentalAPI
    fun start() {
        runBlocking {
            val server = aSocket(ActorSelectorManager(Executors.newCachedThreadPool().asCoroutineDispatcher())).tcp()
                .bind(InetSocketAddress("127.0.0.1", 2323))
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
                            output.write("$line\r\n")
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
 * telnet 127.0.0.1 2323
 */
@KtorExperimentalAPI
fun main(args: Array<String>) {
    EchoServer().start()
}


