# TCP

Packed with information about TCP, bits and bytes. 
And how to put it into action in Kotlin and Java.

## Introduction

### What is [TCP](https://fr.wikipedia.org/wiki/Transmission_Control_Protocol) ? 

TCP stands for Transfer Control Protocol.

TCP is connection-oriented, 
meaning an exclusive connection must first be established between client and server for communication to take place.
The other one is UDP User Datagram Protocol, no connection for this one, it's sending data and hoping for the best.

TCP's job is to ensure that all data sent in a stream moves from Point A to Point B in correct order and intact.

### Where is [HTTP](https://www.extrahop.co.uk/company/blog/2018/tcp-vs-http-differences-explained/)? 

While TCP contains information about what data has or has not yet been received, 
HTTP contains specific instructions on how to read and process this data once it arrives.

HTTP is located at Layer 7 (data, application) of the OSI model ([Open Systems Interconnection](https://fr.wikipedia.org/wiki/Mod%C3%A8le_OSI)), 
TCP is at Layer 4 (segment, transport).  

### Why do we use [Sockets](https://docs.oracle.com/javase/tutorial/networking/sockets/definition.html)?

Socket is an internal endpoint for sending or receiving data between two different processes on the same or different machines. 
It's a way to talk to other computers using standard Unix file descriptors. 

There are 4 types of sockets (stream, datagram, raw, sequenced packet).

Socket is layer 5 (data, Session), two computer should have a socket connection to exchange data. 
You can use TCP for the transport of that data.

## Implementation

We will be using:

```kotlin
import java.net.ServerSocket
import java.net.Socket
```

### Server and Client

There are two concepts here:
  - A server accepts clients connexion. 
    ```kotlin
    val server = ServerSocket(9999)
    val socket = server.accept()
    ``` 
  - A Client looks for a server to establish a connexion.
    ```kotlin
    val socket = Socket("localhost", 9999)
    ```
  
However once the connexion is establish ie the client socket is created and connected to the server.
Then bytes exchange can flow.

### Socket

The Socket is that connexion between the server and the client.
  - Server's socket input is client's socket output.
  - Server's socket output is client's socket input.
  
So basically you read from the input and write to the output. 
You work with Bytes, which might not be the best for your use case. 
For text you can use some wrapper:
  - to write and send text:
  ```kotlin
  PrintWriter(socket.outputStream, true).write("text") 
  ```
  - to read bytes as text
  ```kotlin
  val text = BufferedReader(InputStreamReader(socket.inputStream)).readLine()
  ```
The write is pretty straightforward, you can `flush` the outputStream meaning to forcefully send whatever is in the pipe at that moment.
The reader requires a buffer, which it will use to copy the read bytes into it.

### Multi bind 

When you have one server that needs to accept multiple clients.
Oracle documentation about [TCP Client Server](https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html)
sum it up quite well:

```kotlin
while (true) {
    val socket = server.accept()            // accept a connection
    Thread{ handleClient(socket) }.start()  // create a thread to deal with the client
}
```

### Testing

For testing a connexion, and your tcp server, client.
You have a wide range of possibilities. 

You can either mock the `socket` and the connection to test the logic 
behind the reading and writing of the data, everything is handled well.
You would do that using [mockK](https://mockk.io/) in kotlin

```kotlin
    // Create a mock
    @MockK
    lateinit var mockClientSocket: Socket
    
    // Set the mock up using real OutputStream and InputStream
    fun setup() {
        every { mockClientSocket.getOutputStream() } returns output
        every { mockClientSocket.getInputStream() } returns input
    }
```

Create a real mock server / client that will send the data. With this, you can create a real connexion 
and add some methods to control what is being sent and if it's received back. 
However for simple use case your test client / server might be real close to your main code.

### With netcat

You can use [netcat](https://en.wikipedia.org/wiki/Netcat) use with `nc` to create a tcp server or client using:

```bash
# listens on port 9999
nc -l 9999
# Sends hello to localhost:9999
nc localhost 9999 hello 
telnet localhost 9999
```

## Bits and Bytes

### Representation

A bit is the most basic unit and can be either 1 or 0. 
A [byte](https://www.thethingsnetwork.org/docs/devices/bytes.html) is a group of 8 bits (256 permutations 0000 0000 to 1111 1111)

Thus, one byte can represent a decimal number between 0 and 255. 
Usually computers handles bytes instead of bits.

Then there is [hexadecimal](https://introcs.cs.princeton.edu/java/61data/) of base 16, using both numbers: 0 to 9 and letters: A to F.
Usually you see hex values using `0x` in order not to confuse them with decimal values in certain cases.
```bash
hex     byte        dec  
0x11 	0001 0001   17  

```
However I find it easier to use hex to represent bytes.

![dec - binary - hex conversion](./src/main/resources/bits.png)

### Signed bits

You can also handle negative number with bits using [Two complements](https://en.wikipedia.org/wiki/Two%27s_complement).
With 3 bits from `000` to `111` can give:
  - from 0 to 7 (`000` is 0 and `111` is seven)
  - from -4 to 3 using two's complement (`100` is -4 and `011` is 3)

The two's complement of an N-bit number is defined as its complement with respect to 2N. 
For instance, for the three-bit number 010, the two's complement is 110, because 1000 - 010 = 110 with 1000 a 4bits number and 010 is 0x2.
The two's complement is calculated by inverting the digits and adding one:
 - `010` inverted gives `101` plus `001` gives `110`. 
 - So from 2 (`010`), you get -2 (`110`).      
