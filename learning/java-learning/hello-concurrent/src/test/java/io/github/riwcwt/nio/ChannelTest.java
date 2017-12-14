package io.github.riwcwt.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ChannelTest {

    @Test
    public void copy() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("src/main/resources/test.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("src/main/resources/to.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);

        toChannel.close();
        fromChannel.close();
        toFile.close();
        fromFile.close();
    }

    @Test
    public void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new StringBuilder("current datetime : ").append(System.currentTimeMillis()).toString().getBytes());

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println((char) buffer.get());
        }
    }

    @Test
    public void selector() throws IOException {
        Selector selector = Selector.open();
    }

    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.close();
    }

    @Test
    public void client() {
    }
}
