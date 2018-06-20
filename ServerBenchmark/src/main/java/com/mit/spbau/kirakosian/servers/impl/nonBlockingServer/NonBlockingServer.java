package com.mit.spbau.kirakosian.servers.impl.nonBlockingServer;

import com.mit.spbau.kirakosian.servers.ServerStatsListener;
import com.mit.spbau.kirakosian.servers.exceptions.AbortException;
import com.mit.spbau.kirakosian.servers.impl.AbstractServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("WeakerAccess")
public class NonBlockingServer extends AbstractServer {

    private volatile boolean working = true;
    private final ServerSocketChannel server;

    private Thread mainThread;
    private Thread readingThread;
    private Thread writingThread;
    private final ExecutorService pool = Executors.newFixedThreadPool(3);

    private final Selector reader;
    private final Selector writer;

    private final ConcurrentLinkedQueue<Client> registerRead = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Client> registerWrite = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Client> unregisterRead = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Client> unregisterWrite = new ConcurrentLinkedQueue<>();

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
                    break;
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
        registerWrite(client);
    }

    public void registerRead(final Client client) {
        registerRead.add(client);
        reader.wakeup();
    }

    public void registerWrite(final Client client) {
        registerWrite.add(client);
        writer.wakeup();
    }

    public void unregisterRead(final Client client) {
        unregisterRead.add(client);
        reader.wakeup();
    }

    public void unregisterWrite(final Client client) {
        unregisterWrite.add(client);
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
                while (!unregisterRead.isEmpty()) {
                    final Client client = unregisterRead.poll();
                    if (client == null) {
                        continue;
                    }
                    client.getChannel().keyFor(reader).cancel();
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
                    break;
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
                    client.getChannel().register(writer, SelectionKey.OP_WRITE, client);
                }
                while (!unregisterWrite.isEmpty()) {
                    final Client client = unregisterWrite.poll();
                    if (client == null) {
                        continue;
                    }
                    client.getChannel().keyFor(writer).cancel();
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
                    break;
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

    public ServerStatsListener listener() {
        return listener;
    }

    public ExecutorService pool() {
        return pool;
    }
}
