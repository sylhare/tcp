package tcp.examples

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket

@ExtendWith(MockKExtension::class)
class MockTest {

    private val input: InputStream = mockk()
    private val output: OutputStream = mockk(relaxed = true)
    // Create a mock
    @MockK(relaxUnitFun = true)
    lateinit var mockClientSocket: Socket

    // Set the mock up using real OutputStream and InputStream
    @BeforeEach
    fun setup() {
        every { mockClientSocket.getOutputStream() } returns output
        every { mockClientSocket.getInputStream() } returns input
    }

    @Test
    fun testingWithMock() {
        SocketClient(socket = mockClientSocket).write("data")
        verify { mockClientSocket.getOutputStream() }
    }

    @Test
    fun uncaughtExceptionDummyTest() {
        every { output.write(any(), any(), any()) } throws Exception()
        val client = SocketClient(socket = mockClientSocket)
        assertThrows<Exception> { client.write("data") }
    }

    internal class SocketClient(private val socket: Socket) {

        fun write(data: String) {
            println("Writing $data")
            val output = PrintWriter(socket.getOutputStream(), true)
            output.println(data)
        }
    }
}