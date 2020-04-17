package tcp.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

internal class Client {
    lateinit var client: Socket
    lateinit var output: PrintWriter
    lateinit var input: BufferedReader

    fun startConnection(host: String, port: Int) {
        client = Socket(host, port)
        output = PrintWriter(client.getOutputStream(), true)
        input = BufferedReader(InputStreamReader(client.inputStream))
        println("Client connected : ${client.inetAddress.hostAddress}")
    }

    fun sendMessage(message: String): String {
        println("Client sending [$message]")
        output.println(message)
        return input.readLine()
    }

    fun stopConnection() {
        client.close()
        input.close()
        output.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }
}

fun main() {
    val client = Client()
    client.startConnection("127.0.0.1", 9999)
    client.sendMessage("Hello from the client")
    client.stopConnection()
}