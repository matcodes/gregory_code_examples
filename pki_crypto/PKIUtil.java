/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import javax.crypto.Cipher;

public class PKIUtil {

    private static final String PKI_KEY_TYPE = "RSA";
    private static final String PKI_SIGN_ALGO = "SHA256withRSA";
    private static final String PKI_CRYPT_ALGO = "RSA";

    public static KeyPair generateKeyPair() throws GeneralSecurityException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(PKI_KEY_TYPE);
        keyGen.initialize(1024);
        return keyGen.generateKeyPair();
    }

    public static String signText(PrivateKey privateKey, String plainText) throws GeneralSecurityException {
        Signature sign = Signature.getInstance(PKI_SIGN_ALGO);
        sign.initSign(privateKey);
        sign.update(plainText.getBytes());
        byte[] signature = sign.sign();
        return Base64.encodeBytes(signature);
    }

    public static boolean verifySignatureOnText(PublicKey publicKey, String plaintext, String signature) throws GeneralSecurityException {
        Signature sign = Signature.getInstance(PKI_SIGN_ALGO);
        sign.initVerify(publicKey);
        sign.update(plaintext.getBytes());
        return sign.verify(Base64.decode(signature));
    }

    public static String sign(PrivateKey privateKey, InputStream in) throws GeneralSecurityException {
        try {
            byte[] buf = new byte[4 * 1024];
            Signature sign = Signature.getInstance(PKI_SIGN_ALGO);
            sign.initSign(privateKey);
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                sign.update(buf, 0, numRead);
            }
            return Base64.encodeBytes(sign.sign());
        } catch (IOException ex) {
            throw new GeneralSecurityException(ex);
        }
    }

    public static boolean verifySignature(PublicKey publicKey, InputStream in, String signature) throws GeneralSecurityException {
        try {
            byte[] buf = new byte[4 * 1024];
            Signature sign = Signature.getInstance(PKI_SIGN_ALGO);
            sign.initVerify(publicKey);
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                sign.update(buf, 0, numRead);
            }
            return sign.verify(Base64.decode(signature));
        } catch (IOException ex) {
            throw new GeneralSecurityException(ex);
        }
    }

    public static byte[] rsaEncrypt(Key key, byte[] data) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(PKI_CRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] rsaDecrypt(Key key, byte[] data) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(PKI_CRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static PrivateKey getPrivateKeyFromKeystore(String keyStoreFileLocation, String alias, char[] keyStorePassword, char[] password) throws GeneralSecurityException {
        return getPrivateKeyFromKeystore(keyStoreFileLocation, "JKS", alias, keyStorePassword, password);
    }

    public static PrivateKey getPrivateKeyFromKeystore(String keyStoreFileLocation, String keyStoreType, String alias, char[] keyStorePassword, char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(new FileInputStream(keyStoreFileLocation), keyStorePassword);
            return (PrivateKey) keyStore.getKey(alias, password);
        } catch (FileNotFoundException e) {
            throw new GeneralSecurityException(e);
        } catch (IOException e) {
            throw new GeneralSecurityException(e);
        }
    }

    public static PublicKey getPublicKeyFromKeystore(String keyStoreFileLocation, String alias, char[] keyStorePassword) throws GeneralSecurityException {
        return getCertificateFromKeystore(keyStoreFileLocation, alias, keyStorePassword).getPublicKey();
    }

    public static Certificate getCertificateFromKeystore(String keyStoreFileLocation, String alias, char[] keyStorePassword) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(keyStoreFileLocation), keyStorePassword);
            return keyStore.getCertificate(alias);
        } catch (FileNotFoundException e) {
            throw new GeneralSecurityException(e);
        } catch (IOException e) {
            throw new GeneralSecurityException(e);
        }
    }
}
