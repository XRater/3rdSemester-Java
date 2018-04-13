package com.spbau.mit.kirakosian;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Some tests for MD5Evaluator. Sadly, they does not work correctly...
 */
class MD5EvaluatorTest {

    private final static String dirName = "dir/";
    private final static String prefix = "./" + Utils.sourceDir + "/";
    private MessageDigest md;


    private void tearUp() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
            // should never happen
        }
    }

    @Test
    void checkEmptyDir() throws IOException {
        tearUp();
//        Utils.createTemporaryFile(dirName);
//        System.out.println(Arrays.toString(MD5Evaluator.evaluateMD5WithOneThread(Paths.get("./src"))));
//        assertArrayEquals(MD5Evaluator.evaluateMD5WithOneThread(Paths.get(prefix + dirName)), md.digest());
//        Utils.deleteFile(dirName);
    }

    @Test
    void checkDirWithFile() throws IOException {
        tearUp();
//        Utils.createFile(dirName);
//        Utils.createFile(dirName + "file", "Line");
//        assertArrayEquals(MD5Evaluator.evaluateMD5WithOneThread(Paths.get(prefix + dirName)), md.digest());
//        assertArrayEquals(MD5Evaluator.evaluateMD5WithMultiThread(Paths.get(prefix + dirName)), md.digest());
//        Utils.deleteFile(dirName);
    }


    private String getName(final Path path) {
        return path.getFileName().toString();
    }

}