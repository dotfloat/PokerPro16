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
    private static final Charset charset = Charset.forName("ISO-8859-1");

    private final SocketChannel channel;

    private final ByteArrayOutputStream inputBuffer = new ByteArrayOutputStream();
    private final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();

    private final ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
    private final ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);

    public NetworkIO(SocketChannel channel) throws IOException {
        this.channel = channel;
        this.readByteBuffer.flip();
    }

    public void sendMessage(String message) throws IOException {
        outputBuffer.write(message.getBytes());

        System.out.printf("Sent Message: [%s]\n", message.replace("\r\n", ""));
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
        int wrote = 0;

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(object);

        byte[] bytes = byteStream.toByteArray();

        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        lengthBuffer.clear();
        lengthBuffer.putInt(bytes.length);
        lengthBuffer.flip();

        outputBuffer.write(lengthBuffer.array());
        outputBuffer.write(bytes);

        System.out.printf("Sent Object: [%s] (size: %d)\n", object.getClass(), bytes.length + 4);
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        fillBuffer();

        if (inputBuffer.size() <= 4)
            return null;

        byte[] bytes = inputBuffer.toByteArray();

        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        lengthBuffer.clear();
        lengthBuffer.put(bytes, 0, 4);
        lengthBuffer.flip();

        int length = lengthBuffer.getInt();

        System.out.printf("Object length: %d\n", length);

        if (length + 4 > bytes.length) // Not enough data
            return null;

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes, 4, length);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        inputBuffer.reset();
        inputBuffer.write(bytes, length + 4, bytes.length - (length + 4));

        Object object = objectInputStream.readObject();

        System.out.printf("Received Object: [%s]\n", object.getClass());

        return object;
    }

    /**
     * Attempts to write as much as possible to the SocketChannel
     * object from the internal write buffer.
     * @return number of bytes remaining
     * @throws IOException
     */
    public int flush() throws IOException {
        while (outputBuffer.size() > 0) {

            byte[] bytes = outputBuffer.toByteArray();

            writeByteBuffer.clear();
            writeByteBuffer.flip();

            writeByteBuffer.clear();
            writeByteBuffer.put(bytes, 0, Math.min(bytes.length, 1024));
            writeByteBuffer.flip();

            int written = channel.write(writeByteBuffer);

            if (written > 0) {
                System.out.printf("Written %d, left %d : %d\n", written, bytes.length - written, channel.socket().getSendBufferSize());
                outputBuffer.reset();
                outputBuffer.write(bytes, written, bytes.length - written);
            } else {
                break;
            }
        }

        return outputBuffer.size();
    }

    private int fillBuffer() throws IOException {
        flush();

        int read;
        readByteBuffer.clear();

        if ((read = channel.read(readByteBuffer)) <= 0)
            return read;

        inputBuffer.write(readByteBuffer.array(), 0, readByteBuffer.position());

        return read;
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