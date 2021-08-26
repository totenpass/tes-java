package com.totenpass.tes;

import java.io.Console;
import java.io.PrintWriter;

public class CLI {

    public static void main(String[] args) throws Exception {
        Console c = System.console();
        if (c == null) throw new RuntimeException("Console is required");
        PrintWriter w = c.writer();
        String content;
        if (args.length > 0) {
            content = args[0];
        } else {
            w.println("Enter the encrypted QRCode content:");
            content = c.readLine();
        }
        w.println("Enter your passphrase:");
        char[] passhphrase = c.readPassword();
        Plaintext plaintext = TES.decryptAndDecode(content, passhphrase);
        if (plaintext.isText()) {
            System.out.println("Plaintext:");
            System.out.println(plaintext.getText());
        } else if (plaintext.isFile()) {
            PlaintextFile file = plaintext.getFile();
            System.out.println("Plaintext saved into " + file.filename + " file");
            file.writeTo(file.filename);
        } else {
            System.out.println("No recognized content");
        }
    }
}
