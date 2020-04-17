package bytes

import org.junit.Assert.assertArrayEquals
import org.junit.jupiter.api.Test
import java.net.Socket


internal class EchoByteServerTest {

    private val buffer = ByteArray(1024)

    @Test
    fun serverRespondsToClientOnce() {

        val server = EchoByteServer(9991)
        server.start()
        val clientTest = Socket("127.0.0.1", 9991)
        clientTest.getOutputStream().write("bytes".toByteArray())
        val bufferSize = clientTest.inputStream.read(buffer)
        Thread.sleep(200)
        assertArrayEquals("bytes".toByteArray(), buffer.copyOf(bufferSize))
        server.stop()
    }

    @Test
    fun serverRespondsToClientTwice() {

        val server = EchoByteServer(9991)
        server.start()
        val clientTest = Socket("127.0.0.1", 9991)
        clientTest.getOutputStream().write("bytes".toByteArray())
        clientTest.getOutputStream().flush()
        var bufferSize = clientTest.inputStream.read(buffer)
        Thread.sleep(200)
        assertArrayEquals("bytes".toByteArray(), buffer.copyOf(bufferSize))
        clientTest.getOutputStream().write("other".toByteArray())
        bufferSize = clientTest.inputStream.read(buffer)
        Thread.sleep(200)
        assertArrayEquals("other".toByteArray(), buffer.copyOf(bufferSize))
        server.stop()
    }

}
