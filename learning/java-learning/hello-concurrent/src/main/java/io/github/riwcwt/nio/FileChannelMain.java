package io.github.riwcwt.nio;

//import java.io.File;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
//import java.util.Arrays;

public class FileChannelMain {

    public static void main(String[] args) throws IOException {
        //        Arrays.stream(new File(".").listFiles()).forEach(file -> System.out.println(file.getAbsolutePath()));
        RandomAccessFile file = new RandomAccessFile("src/main/resources/test.txt", "rw");

        FileChannel channel = file.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(8);

        int n = channel.read(buffer);//read into buffer.
        while (n != -1) {
            System.out.println("read:" + n);
            buffer.flip();//make buffer ready for read

            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());// read 1 byte at a time
            }
            buffer.clear(); //make buffer ready for writing
            n = channel.read(buffer);
        }
        file.close();
    }
}
