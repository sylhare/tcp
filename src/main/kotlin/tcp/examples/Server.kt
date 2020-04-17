package tcp.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

internal class Server {

    lateinit var server: ServerSocket
    lateinit var client: Socket
    lateinit var output: PrintWriter
    lateinit var input: BufferedReader


    fun start(port: Int) {
        server = ServerSocket(port)
        println("Server running on port ${server.localPort}")
        client = server.accept()
        output = PrintWriter(client.getOutputStream(), true)
        input = BufferedReader(InputStreamReader(client.inputStream))
        println("Client connected : ${client.inetAddress.hostAddress}")
        handleMessage()
    }

    private fun handleMessage() {
        val message = input.readLine()
        println("Server receiving [${message}]")
        val response = when (message) {
            "hello server" -> "hello client"
            else -> "Danger ... friend"
        }
        println("Server responding [${response}]")
        output.println(response)
    }

    fun stop() {
        try {
            input.close()
            output.close()
            server.close()
        } catch (e: Exception) {
            println(e)
        }
    }
}

fun main() {
    val server = Server()
    server.start(9999)
    server.stop()

}

