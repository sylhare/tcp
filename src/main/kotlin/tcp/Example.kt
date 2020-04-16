package tcp

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.context.annotation.PropertySource
import tcp.Example.Companion.client
import tcp.Example.Companion.server
import java.net.ServerSocket
import java.net.Socket
import java.util.*


/**
 * https://stackoverflow.com/questions/56535473/how-to-send-and-receive-strings-through-tcp-connection-using-kotlin
 */

class Example {

    companion object {
        fun server() {
            val server = ServerSocket(9999)
            println("Server running on port ${server.localPort}")
            val client = server.accept()
            println("Client connected : ${client.inetAddress.hostAddress}")
            val scanner = Scanner(client.inputStream)
            while (scanner.hasNextLine()) {
                println("Server received: ${scanner.nextLine()}")
                break
            }
            server.close()
        }

        fun client() {
            println("Client's ready to stream")
            val client = Socket("127.0.0.1", 9999)
            client.outputStream.write("Hello from the client!".toByteArray())
            client.close()
        }
    }
}

fun main() {
    GlobalScope.launch {
        client()
    }
    server()

}

