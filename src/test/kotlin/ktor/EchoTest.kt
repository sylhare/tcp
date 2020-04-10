package ktor

import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.cio.write
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.net.BindException
import java.net.InetSocketAddress
import kotlin.test.assertEquals

class EchoTest {

    @KtorExperimentalAPI
    @Test
    fun serverTest() {
        GlobalScope.launch {
            EchoServer().start()
        }

        runBlocking {
            val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress("127.0.0.1", 2323))
            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = true)

            output.write("hello\r\n")
            assertEquals("hello", input.readUTF8Line())
        }
    }

    @KtorExperimentalAPI
    @Test
    fun startingTwiceServerCreateBindingExceptionTest() {
        assertThrows(BindException::class.java) { EchoServer().start() }
    }

    @KtorExperimentalAPI
    @Test
    fun clientTest() {
        GlobalScope.launch {
            val server = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().bind(InetSocketAddress("127.0.0.1", 2323))
            val socket = server.accept()

            launch {
                val input = socket.openReadChannel()

                try {
                    val line = input.readUTF8Line()
                    assertEquals("hello", line)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    socket.close()
                }
            }
        }

        EchoClient().start()
    }
}
