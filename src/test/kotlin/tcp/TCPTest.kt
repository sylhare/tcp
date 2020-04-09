package tcp

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class TCPTest {

    companion object {
        private const val port = 9999
        private lateinit var client: Client
        private lateinit var server: Server

        @BeforeAll
        @JvmStatic
        fun setup() {
            GlobalScope.launch {
                server = Server()
                server.start(port)
            }
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            client.stopConnection()
            server.stop()

        }
    }

    @Test
    fun serverRespondsWhenStartedTest() {
        client = Client()
        client.startConnection("127.0.0.1", port)
        val response = client.sendMessage("hello server")
        Assertions.assertEquals("hello client", response)

    }

}