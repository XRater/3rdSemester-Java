package com.mit.spbau.kirakosian;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class Utils {

    public static void writeArray(final DataOutputStream os, final int[] array) throws IOException {
        os.writeInt(array.length);
        for (final int x : array) {
            os.writeInt(x);
        }
    }

    public static int[] readArray(final DataInputStream is) throws IOException {
        final int size = is.readInt();
        final int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = is.readInt();
        }
        return array;
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
}
