package ktor

import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.cio.write
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress

class EchoClient {
    fun start() {
        runBlocking {
            val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress("127.0.0.1", 2323))
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

