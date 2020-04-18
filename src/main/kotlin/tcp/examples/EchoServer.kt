package tcp.examples

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

/**
 * https://rosettacode.org/wiki/Echo_server#Kotlin
 */
class ClientHandler(private val clientSocket: Socket) : Runnable {
    private val connectionId: Int

    init {
        connectionId = ++numConnections
        println("Handling connection, #$connectionId for ${clientSocket.remoteSocketAddress}")
    }

    override fun run() {
        val writer = PrintWriter(clientSocket.outputStream, true)
        val reader = BufferedReader(InputStreamReader(clientSocket.inputStream))
        while (true) {
            val line = reader.readLine() ?: break
            println("Received: $line")
            writer.write("$line\n")
            writer.flush()
            if (line == "exit") break
        }
        reader.close()
        writer.close()
        clientSocket.close()
        println("Closing connection, #$connectionId for ${clientSocket.remoteSocketAddress}")
    }

    private companion object {
        var numConnections = 0
    }
}

fun main(args: Array<String>) {
    val serverSocket = ServerSocket(9999)
    try {
        while (true) {
            Thread(ClientHandler(serverSocket.accept())).start()
        }
    } finally {
        serverSocket.close()
        println("Closing server tcp.socket")
    }
}