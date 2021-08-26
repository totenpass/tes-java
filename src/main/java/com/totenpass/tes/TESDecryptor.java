package com.totenpass.tes;

import com.totenpass.tes.crypto.XChaCha20Poly1305;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.bouncycastle.crypto.params.KeyParameter;

import java.util.Base64;

public class TESDecryptor {

    private static byte[] deriveKey(char[] passphrase, byte[] salt, int iterations, int memoryAsMB) {
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withIterations(iterations)
                .withMemoryAsKB(memoryAsMB * 1024)
                .withSalt(salt);
        Argon2BytesGenerator kdf = new Argon2BytesGenerator();
        kdf.init(builder.build());
        byte[] key = new byte[32];
        kdf.generateBytes(passphrase, key);
        return key;
    }

    private static byte[] decrypt(byte[] key, byte[] data) throws InvalidCipherTextException {
        XChaCha20Poly1305 chaCipher           = new XChaCha20Poly1305();
        if (data.length < chaCipher.NONCE_SIZE) throw new IllegalArgumentException("invalid data: too short");
        byte[] nonce = new byte[chaCipher.NONCE_SIZE];
        System.arraycopy(data, 0, nonce, 0, nonce.length);
        byte[] ciphertext = new byte[data.length - chaCipher.NONCE_SIZE];
        System.arraycopy(data, chaCipher.NONCE_SIZE, ciphertext, 0, ciphertext.length);
        AEADParameters params              = new AEADParameters(new KeyParameter(key), 128, nonce);
        chaCipher.init(false, params);
        byte[] out    = new byte[chaCipher.getOutputSize(ciphertext.length)];
        int outOff = chaCipher.processBytes(ciphertext, 0, ciphertext.length, out, 0);
        chaCipher.doFinal(out, outOff);
        return out;
    }

    public static byte[] decrypt(byte[] data, char[] passphrase) throws InvalidCipherTextException {
        if (data.length < 18) throw new IllegalArgumentException("invalid data: too short");
        if (data[0] != 0) throw new IllegalArgumentException("not TES encryption version 0");
        int iterations = (data[1] >> 5) & 0x07;
        int memory = (data[1] & 0x1F) * 64;
        byte[] salt = new byte[16];
        System.arraycopy(data, 2, salt, 0, salt.length);
        byte[] ciphertext = new byte[data.length - 18];
        System.arraycopy(data, 18, ciphertext, 0, ciphertext.length);
        byte[] key = deriveKey(passphrase, salt, iterations, memory);
        return decrypt(key, ciphertext);
    }

    public static byte[] extractDataFromUrl(String content) {
        int fragmentId = content.indexOf('#');
        String b64data = (fragmentId >= 0) ? content.substring(fragmentId + 1) : content;
        return Base64.getUrlDecoder().decode(b64data);
    }
}
