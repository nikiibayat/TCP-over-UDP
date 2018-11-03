# TCP over UDP

TCP and UDP are both protocols,built on top of the Internet Protocol, used for sending bits of data known as packets.<br />
TCP, the most common protocol on the Internet, stands for Transmission Control Protocol. TCP guarantees in-order transmission of packets and makes sure the recipient recieves all the packets, in other words, TCP guarantees reliability, there is no lost or corrupted packets in TCP transmission.<br />
On the other hand, UDP is unreliable and fast. UDP stands for User Datagram Protocol, a datagram is the same as a data packet. UDP does not guarantees the Receipt of data by the reciever, this means that if a packet is lost, there is no way the reciever can claim it again, however, since all those reliability overheads are gone, it works considerably faster.<br />
This project's objective is to help implementing a TCP protocol based on UDP for educational purposes.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

This project is implemented in JAVA, therefore, in order to run the codes, you need to have JAVA installed on your computer.

### Running the tests

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo


## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds


## Authors

* **Alireza  Roshanzamir**
* **Nicky Bayat**

