package com.totenpass.tes;

public class Plaintext {

    private Object plaintext;

    Plaintext(Object plaintext) {
        this.plaintext = plaintext;
    }

    public boolean isText() {
        return plaintext instanceof String;
    }

    public boolean isFile() {
        return plaintext instanceof PlaintextFile;
    }

    public String getText() {
        return (String) plaintext;
    }

    public PlaintextFile getFile() {
        return (PlaintextFile) plaintext;
    }
}
