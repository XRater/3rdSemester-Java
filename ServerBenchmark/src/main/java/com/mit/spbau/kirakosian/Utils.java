package com.mit.spbau.kirakosian;

import com.google.common.primitives.Ints;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.io.input.BoundedInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    public static void writeArray(final DataOutputStream os, final int[] array) throws IOException {
        final byte[] bytes = makeArrayMessage(array);
        os.writeInt(bytes.length);
        os.write(bytes);
    }

    public static int[] readArray(final DataInputStream is) throws IOException {
        final int size = is.readInt();
        final byte[] array = new byte[size];
        is.read(array);
        return listToArray(ArrayMessage.Array.parseFrom(array).getDataList());
    }

    public static int[] getArray(final byte[] array) throws InvalidProtocolBufferException {
        return listToArray(ArrayMessage.Array.parseFrom(array).getDataList());
    }

    public static void sort(final int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) {
                    final int tmp = array[j];
                    array[j] = array[i];
                    array[i] = tmp;
                }
            }
        }
    }

    public static int[] generate(final int size) {
        final Random random = new Random();
        final int array[] = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    public static byte[] makeArrayMessage(final int[] array) {
        return ArrayMessage.Array.newBuilder().addAllData(arrayToList(array)).build().toByteArray();
    }

    public static int[] listToArray(final List<Integer> list) {
        final int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static List<Integer> arrayToList(final int[] array) {
        final List<Integer> list = new ArrayList<>();
        for (final int i : array) {
            list.add(i);
        }
        return list;
    }
}
