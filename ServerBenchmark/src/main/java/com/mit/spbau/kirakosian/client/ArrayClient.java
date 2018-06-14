package com.mit.spbau.kirakosian.client;

import com.mit.spbau.kirakosian.Utils;
import com.mit.spbau.kirakosian.servers.impl.AbstractServer;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import static com.mit.spbau.kirakosian.Protocol.*;

public class ArrayClient {

    final private DataOutputStream os;
    final private DataInputStream is;

    public ArrayClient() throws IOException {
        Socket socket = new Socket("localhost", AbstractServer.PORT);
        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
    }


    public void work(final int number) throws IOException {
        for (int i = 0; i < number; i++) {
            final int[] array = Utils.generate(1000);

            os.writeInt(NEW_QUERY);
            Utils.writeArray(os, array);
            os.flush();

            final int[] result = Utils.readArray(is);

            System.out.println(Arrays.toString(result));
        }
        os.writeInt(STOP);
        os.flush();
    }
}
