package com.totenpass.tes;

import org.bouncycastle.crypto.InvalidCipherTextException;

public class TES {

    public static Plaintext decryptAndDecode(String content, char[] passphrase) throws InvalidCipherTextException {
        byte[] ciphertextData = TESDecryptor.extractDataFromUrl(content);
        byte[] plaintextData = TESDecryptor.decrypt(ciphertextData, passphrase);
        return TESDecoder.decode(plaintextData);
    }
}
