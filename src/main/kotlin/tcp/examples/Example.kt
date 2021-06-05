import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket


fun server() {
    val server = ServerSocket(9999)
    val client = server.accept()
    val output = PrintWriter(client.getOutputStream(), true)
    val input = BufferedReader(InputStreamReader(client.inputStream))

    output.println("${input.readLine()} back")
}

fun client() {
    val client = Socket("127.0.0.1", 9999)
    val output = PrintWriter(client.getOutputStream(), true)
    val input = BufferedReader(InputStreamReader(client.inputStream))

    println("Client sending [Hello]")
    output.println("Hello")
    println("Client receiving [${input.readLine()}]")
    client.close()
}

fun main() {
    Thread { server() }.start()
    client()
}
