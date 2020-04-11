# TCP

TCP stands for Transfer Control Protocol.

## Introduction

### What is [TCP](https://fr.wikipedia.org/wiki/Transmission_Control_Protocol) ? 

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
## Bits and Bytes

A [byte](https://www.thethingsnetwork.org/docs/devices/bytes.html) is a group of 8 bits (256 permutations 0000 0000 to 1111 1111)
A bit is the most basic unit and can be either 1 or 0. 
