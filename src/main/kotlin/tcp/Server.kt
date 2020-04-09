package tcp

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.*

class Server {

    lateinit var server: ServerSocket
    lateinit var client: Socket
    lateinit var reader: Scanner
    lateinit var output: PrintWriter
    lateinit var input: BufferedReader


    fun start(port: Int) {
        server = ServerSocket(port)
        println("Server running on port ${server.localPort}")
        client = server.accept()
        output = PrintWriter(client.getOutputStream(), true)
        input = BufferedReader(InputStreamReader(client.inputStream))
        println("Client connected : ${client.inetAddress.hostAddress}")
        reader = Scanner(client.inputStream)

        while (reader.hasNextLine()) {
            val message = reader.nextLine()
            println("Server receiving [${message}]")
            output.println(when(message) {
                "hello server" -> "hello client"
                else -> "Danger ... friend"
            })
            break
        }

    }

    fun stop() {
        input.close()
        output.close()
        server.close()
    }
}

fun main() {
    val server = Server()
    server.start(9999)
    server.stop()

}

