import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by xuhuaiyu on 2016/10/13.
 */
public class NIOServer {

	// 服务器端口
	private int port = 10001;
	// 缓冲区大小
	private int blockSize = 4096;
	// 发送数据缓冲区
	private ByteBuffer sendBuffer = ByteBuffer.allocate(blockSize);
	// 接收数据缓冲区
	private ByteBuffer receiveBuffer = ByteBuffer.allocate(blockSize);
	// 选择器
	private Selector selector;

	// 构造方法
	public NIOServer() throws IOException {

		// 打开服务端 channel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 设置 channel 为非阻塞
		serverSocketChannel.configureBlocking(false);
		// 获得 ServerSocket
		ServerSocket serverSocket = serverSocketChannel.socket();
		// 绑定 ip 和 port
		serverSocket.bind(new InetSocketAddress(port));

		// 选择器
		selector = Selector.open();
		// 将 channel 注册到 selector。 注册到selector的channel必须是非阻塞的，也就是说FileChannel不能与selector一起用。
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	// 监听方法
	public void listen() throws IOException {
		// 轮询事件列表
		while(true) {
			selector.select();
			selector.selectedKeys();

		}
	}

	public static void main(String[] args) {



	}
}
