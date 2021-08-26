package com.totenpass.tes;

public class TESDecoder {

    public static Plaintext decode(byte[] data) {
        return new Plaintext(new String(data)); // TODO
    }
}
