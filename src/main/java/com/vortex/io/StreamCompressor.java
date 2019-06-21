package com.vortex.io;

import java.io.*;
import java.util.Arrays;
import java.util.zip.*;

public class StreamCompressor {
    public static void main(String[] args) throws IOException {
        compressString();

        compressFile("d:\\broken_tests.png", "d:\\broken_tests.png.compr");
//        compressFile2("d:\\broken_tests.png", "d:\\broken_tests.png2.compr");
        deCompressFile("d:\\broken_tests.png.compr", "d:\\broken_tests.png.decompr");
    }

    private static void compressString() throws IOException {
        byte[] rawBytes = "aaaaaaaabbbbbbbaaaaaaa".getBytes();
        System.out.println("raw          bytes: " + Arrays.toString(rawBytes));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, new Deflater(9));
        deflaterOutputStream.write(rawBytes);
        deflaterOutputStream.flush();
        deflaterOutputStream.close();

        byte[] compressedBytes = byteArrayOutputStream.toByteArray();
        System.out.println("compressed   bytes: " + Arrays.toString(compressedBytes));


        InflaterInputStream inflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(compressedBytes), new Inflater());

        ByteArrayOutputStream outputStreamDecompressed = new ByteArrayOutputStream();
        int read;
        while ((read = inflaterInputStream.read()) != -1) {
            outputStreamDecompressed.write(read);
        }

        System.out.println("decompressed bytes: " + Arrays.toString(outputStreamDecompressed.toByteArray()));
        System.out.println("decompressed   msg: " + new String(outputStreamDecompressed.toByteArray()));
    }

    private static void compressFile(String srcFile, String dstFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(srcFile));
        FileOutputStream fileOutputStream = new FileOutputStream(new File(dstFile));
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(fileOutputStream, new Deflater(9)); // output stream

        byte[] buff = new byte[1024];
        int numOfBytes;
        while ((numOfBytes = fileInputStream.read(buff)) != -1) {
            deflaterOutputStream.write(buff, 0, numOfBytes);
        }
        deflaterOutputStream.flush();
        deflaterOutputStream.close();
    }

    private static void compressFile2(String srcFile, String dstFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(srcFile));
        FileOutputStream fileOutputStream = new FileOutputStream(new File(dstFile));
        DeflaterInputStream deflaterInputStream = new DeflaterInputStream(fileInputStream, new Deflater(9)); // input stream

        byte[] buff = new byte[1024];
        int numOfBytes;
        while ((numOfBytes = deflaterInputStream.read(buff)) != -1) {
            fileOutputStream.write(buff, 0, numOfBytes);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private static void deCompressFile(String srcFile, String dstFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(srcFile));
        FileOutputStream fileOutputStream = new FileOutputStream(new File(dstFile));
        InflaterInputStream inflaterInputStream = new InflaterInputStream(fileInputStream, new Inflater());

        byte[] buff = new byte[1024];
        int numOfBytes;
        while ((numOfBytes = inflaterInputStream.read(buff)) != -1) {
            fileOutputStream.write(buff, 0, numOfBytes);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }


}
