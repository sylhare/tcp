package tcp.mock

import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets

@ExtendWith(MockKExtension::class)
internal class ServerTest {

    @MockK
    lateinit var mockServerSocket: ServerSocket
    @MockK
    lateinit var mockClientSocket: Socket

    @BeforeEach
    fun setup() {
        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        every { mockClientSocket.getOutputStream() } returns output
        every { mockClientSocket.getInputStream() } returns input
        every { mockClientSocket.isClosed } returns false
        every { mockServerSocket.accept() } returns mockClientSocket
        every { mockServerSocket.close() } just Runs

    }

    @Test
    fun serverTest() {
        val server = Server(mockServerSocket)

        server.listen()
        val reader = BufferedReader(InputStreamReader(mockClientSocket.getInputStream(), StandardCharsets.UTF_8))
        val receivedMessage = reader.readLine()
        Assertions.assertEquals("Hello, world!", receivedMessage)
        verify { mockServerSocket.accept() }
        server.close()
    }
}