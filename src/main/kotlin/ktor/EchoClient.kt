package ktor

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress

class EchoClient {
    @KtorExperimentalAPI
    fun start() {
        runBlocking {
            val socket =
                aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress("127.0.0.1", 2323))
            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = true)

            output.write("hello\r\n")
            val response = input.readUTF8Line()
            println("Server said: '$response'")
        }
    }
}

@KtorExperimentalAPI
fun main(args: Array<String>) {
    EchoClient().start()
}

