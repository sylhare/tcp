package bytes

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.ServerSocket
import java.net.Socket


internal class EchoByteServer(port: Int) {

    private val serverSocket = ServerSocket(port)
    private lateinit var socket: Socket

    fun start() {
        GlobalScope.launch {
            var buffer = ByteArray(1024)
            socket = serverSocket.accept()
            val inputStream = socket.getInputStream()
            val outputStream = socket.getOutputStream()
            while (true) {
                val bufferSize = inputStream.read(buffer)
                val receivedBytes = buffer.copyOf(bufferSize)
                println(String(receivedBytes))
                outputStream.write(receivedBytes)
            }
        }
    }

    fun stop() {
        socket.close()
        serverSocket.close()
    }
}