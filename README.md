# TCP over UDP
This project's objective is to help implementing a TCP protocol based on UDP **for educational purposes**.<br />

## Background
TCP and UDP are both protocols,built on top of the Internet Protocol, used for sending bits of data known as packets.<br />
TCP, the most common protocol on the Internet, stands for Transmission Control Protocol. TCP guarantees in-order transmission of packets and makes sure the recipient recieves all the packets, in other words, TCP guarantees reliability, there is no lost or corrupted packets in TCP transmission.<br />
On the other hand, UDP is unreliable and fast. UDP stands for User Datagram Protocol, a datagram is the same as a data packet. UDP does not guarantees the Receipt of data by the reciever, this means that if a packet is lost, there is no way the reciever can claim it again, however, since all those reliability overheads are gone, it works considerably faster.<br />
## Introduction
This project will help implementing a TCP protocol using UDP transmission, students are not allowed to use tcp transmission and they are asked to implement the differences between these two protocols and transmit using udp. For example, tcp 3-way handshake or the send/recieve methods should be implemented by students themselves. In this regard, we have implemented a new version of Datagram Socket called Enhanced Datagram Socket that simplifize assessment and evaluation of the TCP protocol implemented by students. It is an obligation for students to transmit packets using Enhanced Datagram Socket.

### Enhanced Datagram Socket
Enhanced Datagram Socket extends JAVA DatagramSocket, therefore, it aquires all the properties and behaviours of its parent. Students must pay attention that they can use all the methods and fields of DatagramSocket that is inherited to Enhanced Datagram Socket, however, they can never use DatagramSocket itself for transmitting data.<br />
In EnhancedDatagramSocket.java the default value of payload limit, loss rate, delay and the sampling period of sent bytes are adjustable in order to simulate the characteristics of an actual link. Enhanced Datagram Socket sends packets of data based on the loss rate and the delay specified in this file. Simultanously, it plots the bytes of data sent per milliseconds, this way the actual transmission of data can be assessed.
![BytesPerTime](https://github.com/nikiibayat/TCP-over-UDP/blob/master/images/BytesPerTime.png?raw=true "BytesPerTime")

### TCP Socket
Students are asked to complete this class in TCPSocketImpl.java, there are five methods in TCPSocketImpl that students have to complete: send, receive, close, getSSThreshold and getWindowSize.<br />
the **send** method, sends the file that its address is recieved in input to the destination, and **recieve** method, will recieve data and write it to the input address.<br />
send and receive should be implemented based on the automatic repeat-request (ARQ) protocol assigned to students by their teaching assistant, for example, if students are asked to implement Go-Back-N protocol, the send and recieve should always be made reliable using Go-Back-N. For implementing these protocols you can use this link [Selective Repeat / Go Back N](http://www.ccs-labs.org/teaching/rn/animations/gbn_sr/). The reliability of the implemented TCP will be validated by increasing the loss rate during transmission.<br />
If TCP Congestion Control is required, then student should complete getWindowSize and getSSThreshold methods as well as the algorithm itself. **getWindowSize** should return the size of current window and **getSSThreshold** should return current value of slow start threshold. **onWindowChange** should be called whenever window size or ssthreshold changes. these three methods will draw slow start and window size per time which helps in assessing congestion control implementation.
![CongestionWindow](https://github.com/nikiibayat/TCP-over-UDP/blob/master/images/CongestionWindow.png?raw=true "CongestionWindow")<br />
The **close** method should be implemented as specified in each semester's project instruction. If asked, then the protocol of closing connection in TCP should be implemented in this method.

### TCP Server Socket
Students are asked to complete this class in TCPServerSocketImpl.java, there are two methods in TCPServerSocketImpl that students have to complete: accept and close.<br />
Server waits on the assigned port for a client to send syn packet, then TCP 3-Way Handshake begins. Handshake on server side, should be implemented in accept method and **must** be reliable, furthermore implementing essential tcp flags for this part is mandatory. If asked on the project instructions, then the server should be able to support Multiplexing and Demultiplexing.<br />
the **close** method should be implemented as specified in each semester's project instruction. If asked, then the protocol of closing connection in TCP should be implemented in this method.

## Getting Started

First of all, it is advised that you implement setting up TCP connection between server and client using a reliable TCP 3-Way Handshake, then you can move on to further requirements of the project. <br />
![3-way handshake](https://github.com/nikiibayat/TCP-over-UDP/blob/master/images/handshake.png?raw=true "3-way handshake")<br />


### Prerequisites

This project is implemented in JAVA, therefore, in order to run the codes, you need to have JAVA installed on your computer.<br />
project-jdk-name="1.8" <br />
project-jdk-type="JavaSDK" <br />

### Running the tests

in order to test your tcp send/recieve you can use Sender.java and Reciever.java. An example usage of these classes is implemented for you. you can use "1MB.txt" in this repository to test your code, however you should notice that examiners might use other files or default values and your code is expected to work in any case. <br />

follow the instructions below:<br />

in Sender.java:
```
tcpSocket.send("the path in your computer/1MB.txt");
```
in Receiver.java:
```
tcpSocket.receive("the path in your computer");
```
It is obvious that the server must be running before the client starts establishing the connection, therefore consider this when executing your code.<br />
Firstly, try with loss rate = 0, if the file is received completely, then slightly increase the loss rate  (for example, loss rate = 0.1) this will examine the reliability of your transmission. If your transmission is fast, then you could try increasing the default value of delay.<br />
If your congestion control algorithm is implemented accurately, then the tcp congestion control plot that is drawed after the transmission is one way to evaluate it. 

## Built With

* [Java8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html/) - Development Platform


## Authors

* **Alireza  Roshanzamir**
* **Nicky Bayat**

