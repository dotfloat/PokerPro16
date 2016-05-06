package org.gruppe2.network;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Arrays;


/**
 * Reader and writer for net protocol
 */
public class NetworkIO {
    private final SocketChannel channel;
    private final ByteArrayOutputStream inputBuffer = new ByteArrayOutputStream();
    private final ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
    private final Charset charset = Charset.forName("ISO-8859-1");

    public NetworkIO(SocketChannel channel) throws IOException {
        this.channel = channel;
        this.readByteBuffer.flip();
    }

    public void sendMessage(String message) throws IOException {
        byte[] bytes = message.getBytes(charset);
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.clear();
        buffer.put(bytes);
        buffer.flip();

        System.out.printf("Sent Message: [%s]\n", message.replace("\r\n", ""));

        channel.write(buffer);
    }

    public String[] readMessage() throws IOException {
        fillBuffer();

        if (inputBuffer.size() <= 0)
            return null;

        byte[] bytes = inputBuffer.toByteArray();

        int crlf = -1;

        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == (byte) '\r' && bytes[i + 1] == (byte) '\n') {
                crlf = i;
                break;
            }
        }

        if (crlf < 0)
            return null;

        String message = new String(bytes, 0, crlf, charset);
        inputBuffer.reset();
        inputBuffer.write(bytes, crlf + 2, bytes.length - (crlf + 2));

        System.out.printf("Received Message: [%s]\n", message);

        return splitMessage(message);
    }

    private void fillBuffer() throws IOException {
        readByteBuffer.clear();

        channel.read(readByteBuffer);

        inputBuffer.write(readByteBuffer.array(), 0, readByteBuffer.position());
    }

    private String[] splitMessage(String msg) {
        int indexOfColon = msg.indexOf(":");

        if (indexOfColon < 0) {
            return msg.split(";");
        }

        String[] args = msg.substring(0, indexOfColon).split(";");
        String rest = msg.substring(indexOfColon + 1);

        String[] output = Arrays.copyOf(args, args.length + 1);

        output[args.length] = rest;

        return output;
    }
}