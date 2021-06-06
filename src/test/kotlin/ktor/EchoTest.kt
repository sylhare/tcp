package ktor

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
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

    private val echoServer = EchoServer()

    @Test
    fun serverTest() {
        GlobalScope.launch {
            echoServer.start()
        }
        Thread.sleep(100)
        runBlocking {
            val socket =
                aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress("127.0.0.1", 2224))
            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = true)

            output.writeFully("hello\r\n".toByteArray())
            assertEquals("hello", input.readUTF8Line())
        }
    }

    @Test
    fun clientTest() {
        EchoClient().start()
    }

    @Test
    fun startingTwiceServerCreateBindingExceptionTest() {
        assertThrows(BindException::class.java) { EchoServer().start() }
    }
}
