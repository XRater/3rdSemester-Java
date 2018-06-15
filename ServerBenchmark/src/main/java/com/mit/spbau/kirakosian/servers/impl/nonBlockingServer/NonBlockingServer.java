package com.mit.spbau.kirakosian.servers.impl.nonBlockingServer;

import com.mit.spbau.kirakosian.servers.exceptions.AbortException;
import com.mit.spbau.kirakosian.servers.impl.AbstractServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NonBlockingServer extends AbstractServer {

    private volatile boolean working = true;
    private final ServerSocketChannel server;

    private Thread mainThread;
    private Thread readingThread;
    private Thread writingThread;

    private final Selector reader;
    private final Selector writer;

    private final ConcurrentLinkedQueue<Client> registerRead = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Client> registerWrite = new ConcurrentLinkedQueue<>();

    public NonBlockingServer() throws AbortException {
        try {
            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(PORT));

            reader = Selector.open();
            writer = Selector.open();
        } catch (final IOException e) {
            throw new AbortException();
        }
    }

    @Override
    public void start() {
        working = true;
        mainThread = new Thread(this::work);
        readingThread = new Thread(this::processRead);
        writingThread = new Thread(this::processWrite);
        mainThread.start();
        readingThread.start();
        writingThread.start();
    }

    private void work() {
        while (working) {
            try {
                final SocketChannel channel = server.accept();
                processConnection(channel);
            } catch (final IOException e) {
                if (working) {
                    listener.fail(e);
                }
                break;
            }
        }

        closeAll();
    }

    private void processConnection(final SocketChannel channel) {
        final Client client = new Client(channel, this);
        try {
            channel.configureBlocking(false);
        } catch (final IOException e) {
            listener.fail(e);
        }
        registerRead(client);
    }

    public void registerRead(final Client client) {
        registerRead.add(client);
        reader.wakeup();
    }

    public void registerWrite(final Client client) {
        registerWrite.add(client);
        writer.wakeup();
    }

    private void processRead() {
        while (working) {
            try {
                while (!registerRead.isEmpty()) {
                    final Client client = registerRead.poll();
                    if (client == null) {
                        continue;
                    }
                    client.getChannel().register(reader, SelectionKey.OP_READ, client);
                }

                final int ready = reader.select();
                if (ready == 0) {
                    continue;
                }
                final Iterator<SelectionKey> iterator = reader.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    final SelectionKey key = iterator.next();
                    final Client client = (Client) key.attachment();
                    client.onRead();
                    iterator.remove();
                }
            } catch (final IOException e) {
                if (working) {
                    listener.fail(e);
                }
                break;
            }
        }

        closeAll();
    }

    private void processWrite() {
        while (working) {
            try {
                while (!registerWrite.isEmpty()) {
                    final Client client = registerWrite.poll();
                    if (client == null) {
                        continue;
                    }
                    client.getChannel().register(reader, SelectionKey.OP_WRITE, client);
                }

                final int ready = writer.select();
                if (ready == 0) {
                    continue;
                }
                final Iterator<SelectionKey> iterator = writer.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    final SelectionKey key = iterator.next();
                    final Client client = (Client) key.attachment();
                    client.onWrite();
                    iterator.remove();
                }
            } catch (final IOException e) {
                if (working) {
                    listener.fail(e);
                }
                break;
            }
        }

        closeAll();
    }

    private void closeAll() {
        if (working) {
            working = false;
            try {
                reader.close();
                writer.close();
                server.close();
                mainThread.join();
                readingThread.join();
                writingThread.join();
            } catch (final IOException | InterruptedException e) {
                listener.fail(e);
            }
        }
    }

    @Override
    public synchronized void shutDown() {
        if (!working) {
            return;
        }
        closeAll();
    }
}
