package org.gruppe2.network;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

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

    /**
     * Attempts to read a string that ends with "\r\n" and returns a split array of arguments
     *
     * @return Array of arguments or null if unsuccessful
     * @throws IOException
     */
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

    public void sendObject(Object object) throws IOException {
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(object);

        byte[] bytes = byteOutputStream.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + 4);
        byteBuffer.clear();
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();

        channel.write(byteBuffer);

        System.out.printf("Sent Object: [%s]\n", object.getClass());
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        fillBuffer();

        if (inputBuffer.size() <= 0)
            return null;

        byte[] bytes = inputBuffer.toByteArray();

        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        lengthBuffer.clear();
        lengthBuffer.put(bytes, 0, 4);
        lengthBuffer.flip();

        int length = lengthBuffer.getInt();

        if (length + 4 >= bytes.length) // Not enough data
            return null;

        ByteInputStream byteInputStream = new ByteInputStream(bytes, 4, length);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);

        inputBuffer.reset();
        inputBuffer.write(bytes, length + 4, bytes.length - (length + 4));

        Object object = objectInputStream.readObject();

        System.out.printf("Received Object: [%s]\n", object.getClass());

        return object;
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