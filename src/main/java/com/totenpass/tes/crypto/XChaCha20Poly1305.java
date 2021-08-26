package com.totenpass.tes.crypto;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class XChaCha20Poly1305 extends ChaCha20Poly1305 {

    public static final int NONCE_SIZE = 24;

    @Override
    public void init(boolean forEncryption, CipherParameters params) throws IllegalArgumentException {
        if (params instanceof AEADParameters) {
            AEADParameters aeadParams = (AEADParameters) params;
            if (aeadParams.getNonce().length != NONCE_SIZE)
                throw new IllegalArgumentException("'nonce' must be 24-bytes");
            byte[] hKey = aeadParams.getKey().getKey();
            byte[] hNonce = new byte[16];
            System.arraycopy(aeadParams.getNonce(), 0, hNonce, 0, hNonce.length);
            byte[] key = HChaCha20.hChaCha20(hKey, hNonce);
            byte[] nonce = new byte[12];
            System.arraycopy(aeadParams.getNonce(), 16, nonce, 4, nonce.length - 4);
            AEADParameters newParams = new AEADParameters(new KeyParameter(key), aeadParams.getMacSize(), nonce, aeadParams.getAssociatedText());
            super.init(forEncryption, newParams);
        } else {
            throw new IllegalArgumentException("invalid parameters passed to XChaCha20Poly1305");
        }
    }
}
