package mock

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket

internal class Server(val socket: ServerSocket) {

    fun listen() {
        GlobalScope.launch {
            listen(socket)
        }
    }

    @Throws(IOException::class)
    fun close() {
        socket.close()
    }

}


fun listen(socket: ServerSocket) {
    val client = socket.accept()
    val output = PrintWriter(client.getOutputStream(), true)
    while (!client.isClosed) {
        output.println("Hello, world!")
        Thread.sleep(100)
    }
}