package com.totenpass.tes;

import java.nio.charset.StandardCharsets;

public class TESDecoder {

    public static Plaintext decode(byte[] data) {
        if (data.length < 2) throw new IllegalArgumentException("invalid data: too short");
        if (data[0] != 0) throw new IllegalArgumentException("not TES plaintext version 0");
        switch (data[1]) {
            case 0:
                return new Plaintext(new String(data, 2, data.length - 2, StandardCharsets.UTF_8));
            case 1:
                int terminator = 2;
                while (terminator < data.length && data[terminator] != 0) terminator++;
                if (terminator == data.length) throw new IllegalArgumentException("invalid plaintext file format");
                String filename = new String(data, 2, terminator - 2, StandardCharsets.UTF_8);
                byte[] content = new byte[data.length - terminator - 1];
                System.arraycopy(data, terminator + 1, content, 0, content.length);
                return new Plaintext(new PlaintextFile(filename, content));
            default:
                throw new IllegalArgumentException("invalid data: unsupported content for version 0");
        }
    }
}
