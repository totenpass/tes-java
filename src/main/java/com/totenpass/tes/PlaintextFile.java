package com.totenpass.tes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PlaintextFile {

    public String filename;
    public byte[] content;

    public PlaintextFile(String filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }

    public void writeTo(OutputStream os) throws IOException {
        os.write(content);
    }

    public void writeTo(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        try {
            writeTo(fos);
        } finally {
            fos.close();
        }
    }
}
