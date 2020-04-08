package tcp

import java.net.ServerSocket
import java.util.*

fun main() {
    val server = ServerSocket(9999)
    println("Server running on port ${server.localPort}")
    val client = server.accept()
    println("Client connected : ${client.inetAddress.hostAddress}")
    val scanner = Scanner(client.inputStream)
    while (scanner.hasNextLine()) {
        println(scanner.nextLine())
        break
    }
    server.close()
}