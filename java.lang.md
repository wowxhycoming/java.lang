# IO

## 基本概念解析

### 阻塞和非阻塞

阻塞和非阻塞是进程在访问数据的时候，数据内是否准备就绪的一种处理方式。

阻塞：当数据没有准备的时候，往往需要等待缓冲区中的数据准备好过后才处理其他的事情，否则一直等待在那里。

非阻塞:当我们的进程访问我们的数据缓冲区的时候，数据没有准备好的时候，直接返回，不需要等待。数据有的时候 也直接返回。

### 同步和异步的方式

同步和异步都是基于应用程序和操作系统处理IO时间锁采用的方式。同步应用程序要直接参与IO读写的操作；异步应用程序所有的IO读写交给操作系统去处理。

同步的方式在处理IO事件的时候，必须阻塞在某个方法上面等待IO事件完成(阻塞IO事件或通过轮询IO事件的方式)。

对于异步来说，所有的IO读写都交给了操作系统，这个时候应用程序可以去做其他的事情，并不需要去完成真正的IO操作，当操作完成IO后，给应用程序一个通知就可以了。

### IO事件的轮询  --多路复用技术(select模式)

读写事件交给一个单独的线程来处理，这个完成IO事件的注册功能，还有不断的去轮询我们的读写缓冲区，看是否有数据准备好，如果准备好就通知相应读写线程。这样以前的读写线程就可以做其他的事情，这时阻塞的不是所有的IO线程，阻塞的select这个线程。

阻塞到了selector，这仍然是一种同步方式。

## Java IO模型

### BIO

在jdk.14之前使用的都是bio，io操作都阻塞到读写方法（read或write）。解决方案是启用多线程，让一个线程专门阻塞到读写操作。这样对系统开销比较浪费。

### NIO（NIO1） 同步非阻塞

从jdk1.4开始，采用了多路复用技术，实现了IO事件的轮询的方式，达到了同步非阻塞。目前主流的IO操作方式。

mina2、netty5 都是实现了NIO1的框架。

### AIO（NIO2） 异步IO

从jdk1.7开始，达到了异步IO的支持。（从linux学习了epoll模式。）

## 原理

对于网络通信，NIO和AIO并没有改变网络通信步骤，只是在其原来的基础上（serversocket和socket）做了一个改进。

socket 和 serversocket 通过三次握手进行连接，这样建立稳定连接的开销比较大。减少连接次数，对读写通道抽象成管道。

一个TCP连接可以对应多个channel，而不是以前一个通信信道，减少了TCP连接的次数。

UDP也采用了同样的方式。

### NIO模型原理

通过selector（选择器）管理IO事件。IO事件会注册selector，selector会分配给这个IO时间一个key（可以简单理解为一个事件标签），当事件完成，通过key找到对应的管道，然后通过管道发送数据和接收数据。

## NIO Channel

1. FileChannel
FileChannel 从文件中读写数据。阻塞 channel 。

2. DatagramChannel
DatagramChannel 能通过UDP读写网络中的数据。

3. SocketChannel
SocketChannel 能通过TCP读写网络中的数据。

4. ServerSocketChannel
ServerSocketChannel可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。

## NIO Buffer

1. ByteBuffer
2. MappedByteBuffer
3. CharBuffer
4. DoubleBuffer
5. FloatBuffer
6. IntBuffer
7. LongBuffer
8. ShortBuffer

## NIO Selector
