package com.totenpass.tes.crypto;

import org.bouncycastle.util.Pack;
import org.bouncycastle.util.Strings;

public class HChaCha20 {

    private static int[] SIGMA = Pack.littleEndianToInt(Strings.toByteArray("expand 32-byte k"), 0, 4);

    public static byte[] hChaCha20(byte[] key, byte[] nonce) throws IllegalArgumentException {
        byte[] out = new byte[32];
        hChaCha20(out, key, nonce);
        return out;
    }

    private static void hChaCha20(byte[] out, byte[] key, byte[] nonce) throws IllegalArgumentException {
        if (out.length != 32) throw new IllegalArgumentException("'out' must be 32-bytes");
        if (key.length != 32) throw new IllegalArgumentException("'key' must be 32-bytes");
        if (nonce.length != 16) throw new IllegalArgumentException("'nonce' must be 16-bytes");

        int x00 = SIGMA[0];
        int x01 = SIGMA[1];
        int x02 = SIGMA[2];
        int x03 = SIGMA[3];
        int x04 = Pack.littleEndianToInt(key, 0);
        int x05 = Pack.littleEndianToInt(key, 4);
        int x06 = Pack.littleEndianToInt(key, 8);
        int x07 = Pack.littleEndianToInt(key, 12);
        int x08 = Pack.littleEndianToInt(key, 16);
        int x09 = Pack.littleEndianToInt(key, 20);
        int x10 = Pack.littleEndianToInt(key, 24);
        int x11 = Pack.littleEndianToInt(key, 28);
        int x12 = Pack.littleEndianToInt(nonce, 0);
        int x13 = Pack.littleEndianToInt(nonce, 4);
        int x14 = Pack.littleEndianToInt(nonce, 8);
        int x15 = Pack.littleEndianToInt(nonce, 12);

        for (int i = 0; i < 20; i += 2) {
            // Diagonal round.
            x00 += x04;
            x12 = Integer.rotateLeft(x12 ^ x00, 16);
            x08 += x12;
            x04 = Integer.rotateLeft(x04 ^ x08, 12);
            x00 += x04;
            x12 = Integer.rotateLeft(x12 ^ x00, 8);
            x08 += x12;
            x04 = Integer.rotateLeft(x04 ^ x08, 7);

            x01 += x05;
            x13 = Integer.rotateLeft(x13 ^ x01, 16);
            x09 += x13;
            x05 = Integer.rotateLeft(x05 ^ x09, 12);
            x01 += x05;
            x13 = Integer.rotateLeft(x13 ^ x01, 8);
            x09 += x13;
            x05 = Integer.rotateLeft(x05 ^ x09, 7);

            x02 += x06;
            x14 = Integer.rotateLeft(x14 ^ x02, 16);
            x10 += x14;
            x06 = Integer.rotateLeft(x06 ^ x10, 12);
            x02 += x06;
            x14 = Integer.rotateLeft(x14 ^ x02, 8);
            x10 += x14;
            x06 = Integer.rotateLeft(x06 ^ x10, 7);

            x03 += x07;
            x15 = Integer.rotateLeft(x15 ^ x03, 16);
            x11 += x15;
            x07 = Integer.rotateLeft(x07 ^ x11, 12);
            x03 += x07;
            x15 = Integer.rotateLeft(x15 ^ x03, 8);
            x11 += x15;
            x07 = Integer.rotateLeft(x07 ^ x11, 7);

            // Column round.
            x00 += x05;
            x15 = Integer.rotateLeft(x15 ^ x00, 16);
            x10 += x15;
            x05 = Integer.rotateLeft(x05 ^ x10, 12);
            x00 += x05;
            x15 = Integer.rotateLeft(x15 ^ x00, 8);
            x10 += x15;
            x05 = Integer.rotateLeft(x05 ^ x10, 7);

            x01 += x06;
            x12 = Integer.rotateLeft(x12 ^ x01, 16);
            x11 += x12;
            x06 = Integer.rotateLeft(x06 ^ x11, 12);
            x01 += x06;
            x12 = Integer.rotateLeft(x12 ^ x01, 8);
            x11 += x12;
            x06 = Integer.rotateLeft(x06 ^ x11, 7);

            x02 += x07;
            x13 = Integer.rotateLeft(x13 ^ x02, 16);
            x08 += x13;
            x07 = Integer.rotateLeft(x07 ^ x08, 12);
            x02 += x07;
            x13 = Integer.rotateLeft(x13 ^ x02, 8);
            x08 += x13;
            x07 = Integer.rotateLeft(x07 ^ x08, 7);

            x03 += x04;
            x14 = Integer.rotateLeft(x14 ^ x03, 16);
            x09 += x14;
            x04 = Integer.rotateLeft(x04 ^ x09, 12);
            x03 += x04;
            x14 = Integer.rotateLeft(x14 ^ x03, 8);
            x09 += x14;
            x04 = Integer.rotateLeft(x04 ^ x09, 7);
        }

        Pack.intToLittleEndian(x00, out, 0);
        Pack.intToLittleEndian(x01, out, 4);
        Pack.intToLittleEndian(x02, out, 8);
        Pack.intToLittleEndian(x03, out, 12);
        Pack.intToLittleEndian(x12, out, 16);
        Pack.intToLittleEndian(x13, out, 20);
        Pack.intToLittleEndian(x14, out, 24);
        Pack.intToLittleEndian(x15, out, 28);
    }

}
