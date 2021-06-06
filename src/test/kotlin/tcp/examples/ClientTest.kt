package tcp.examples

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException

internal class ClientTest {

    companion object {
        private const val port = 9999
        private lateinit var client: Client
        private lateinit var server: Server

        @BeforeAll
        @JvmStatic
        fun setup() {
            Thread(Runnable {
                server = Server()
                try {
                    server.start(port)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }).start()
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            server.stop()
        }
    }

    @Test
    fun serverRespondsHelloClient() {
        client = Client()
        client.startConnection("127.0.0.1", port)
        val response = client.sendMessage("Hello server")
        Assertions.assertEquals("Hello client", response)
        client.stopConnection()
    }

    @Test
    fun serverRespondsSomethingElse() {
        tearDown() // Things gets weird
        setup()
        client = Client()
        client.startConnection("127.0.0.1", port)
        val response = client.sendMessage("Yo!")
        Assertions.assertNotEquals("Hello client", response)
        client.stopConnection()
    }

}
