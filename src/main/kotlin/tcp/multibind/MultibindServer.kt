package tcp.multibind

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket


@Throws(IOException::class)
fun main(args: Array<String>) {
    val serverSocket = ServerSocket(9999)
    while (true) {
        val client = serverSocket.accept()  // accept a connection
        SocketHandler(client).start()               // create a thread to deal with the client
        println("... New client connected")
    }
}


class SocketHandler internal constructor(var client: Socket) : Thread() {
    override fun run() {
        while (client.isConnected) {
            try {
                val input = BufferedReader(InputStreamReader(client.getInputStream()))
                val output = PrintWriter(client.getOutputStream(), true)
                val received = input.readLine()
                println(received)
                output.write("Received $received\n")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}