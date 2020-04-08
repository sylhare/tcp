package tcp

import java.net.Socket

/**
 * https://stackoverflow.com/questions/56535473/how-to-send-and-receive-strings-through-tcp-connection-using-kotlin
 */
fun main() {
    val client = Socket("127.0.0.1", 9999)
    client.outputStream.write("Hello from the client!".toByteArray())
    client.close()
}