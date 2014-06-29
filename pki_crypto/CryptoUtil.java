/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {

    public static final String LFS_MAGIC_HEADER = "__LFS__";
    private static final String CRYPTO_KEY_TYPE = "AES";
    private static final String CRYPTO_ALGO = "AES/CBC/PKCS5Padding";
    // Create an 16-byte initialization vector
    private static final byte[] iv = new byte[]{
        (byte) 0x8E, 0x12, 0x39, (byte) 0x9C,
        0x07, 0x72, 0x6F, 0x5A, (byte) 0x8E, 0x12, 0x39, (byte) 0x9C,
        0x07, 0x72, 0x6F, 0x5A
    };

    // Create an 16-byte initialization vector
    private static final byte[] encryptionKey = new byte[]{
        (byte) 0x8E, 0x12, 0x39, (byte) 0x9C,
        0x07, 0x72, 0x6F, 0x5A, (byte) 0x8E, 0x12, 0x39, (byte) 0x9C,
        0x07, 0x72, 0x6F, 0x5A
    };

    public static SecretKey generateEncryptionKey() throws GeneralSecurityException {
        // Initializing and Serializing key object.
        return KeyGenerator.getInstance(CRYPTO_KEY_TYPE).generateKey();
    }

    private static void writeToStream(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[4 * 1024];
        int numRead = 0;
        while ((numRead = in.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
        }
    }

    public static boolean isMagicHeaderPresent(InputStream in) throws IOException {
        try {
            in.mark(LFS_MAGIC_HEADER.length());
            byte[] magicHeader = new byte[LFS_MAGIC_HEADER.length()];
            int magic = in.read(magicHeader);
            if (magic < LFS_MAGIC_HEADER.length()) {
                return false;
            }
            return (LFS_MAGIC_HEADER.equals(new String(magicHeader)));
        } finally {
            in.reset();
        }
    }

    private static void encryptOrDecrypt(InputStream in, SecretKey key, OutputStream out, int encDecMode, boolean withHeader) throws IOException, GeneralSecurityException {
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(CRYPTO_ALGO);
        // CBC requires an initialization vector
        cipher.init(encDecMode, key, paramSpec);
        // Bytes written to out will be encrypted
        if (encDecMode == Cipher.ENCRYPT_MODE) {
            out.write(LFS_MAGIC_HEADER.getBytes());
        } else {
            byte[] magicHeader = new byte[LFS_MAGIC_HEADER.length()];
            int bytesRead = in.read(magicHeader);
            if (bytesRead < LFS_MAGIC_HEADER.length() || LFS_MAGIC_HEADER.equals(new String(magicHeader)) != true) {
                throw new IOException("Invalid Cipher Header");
            }
        }
        out = new CipherOutputStream(out, cipher);
        // Read in the cleartext bytes and write to out to encrypt
        writeToStream(in, out);
        out.flush();
        out.close();
    }

    public static SecretKey getKeyFromByteArray(byte[] keyBytes) throws GeneralSecurityException {
        return new SecretKeySpec(keyBytes, CRYPTO_KEY_TYPE);
    }

    public static void encrypt(InputStream in, SecretKey key, OutputStream out) throws IOException, GeneralSecurityException {
        encrypt(in, key, out, false);
    }

    public static void encrypt(InputStream in, SecretKey key, OutputStream out, boolean generateHeader) throws IOException, GeneralSecurityException {
        encryptOrDecrypt(in, key, out, Cipher.ENCRYPT_MODE, true);
    }

    public static void encrypt(InputStream in, OutputStream out) throws IOException, GeneralSecurityException {
        encrypt(in, getKeyFromByteArray(encryptionKey), out);
    }

    public static void decrypt(InputStream in, SecretKey key, OutputStream out) throws IOException, GeneralSecurityException {
        decrypt(in, key, out, false);
    }

    public static void decrypt(InputStream in, SecretKey key, OutputStream out, boolean headerPresent) throws IOException, GeneralSecurityException {
        encryptOrDecrypt(in, key, out, Cipher.DECRYPT_MODE, true);
    }

    public static void decrypt(InputStream in, OutputStream out) throws IOException, GeneralSecurityException {
        decrypt(in, getKeyFromByteArray(encryptionKey), out);
    }

    private static void doEncryptionTest() throws IOException, GeneralSecurityException {


        SecretKey key = CryptoUtil.generateEncryptionKey();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String inputString = "Quick Brown Fox Ate a Lion";
        System.out.println("Key Length " + key.getEncoded().length);
        String keyStr = Base64.encodeBytes(key.getEncoded());
        System.out.println(keyStr);
        CryptoUtil.encrypt(new ByteArrayInputStream(inputString.getBytes()), bos);
        System.out.println(new String(bos.toByteArray()));
        SecretKey key1 = new SecretKeySpec(Base64.decode(keyStr), "AES");
        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        CryptoUtil.decrypt(new ByteArrayInputStream(bos.toByteArray()), bos1);
        if (inputString.equals(new String(bos1.toByteArray()))) {
            System.out.println("Encryption Decryption Test Success");
        } else {
            System.out.println("Encryption Decryption Test FAILED!!!");
        }
    }

    public static void main(String[] args) throws Exception {
        doEncryptionTest();
        if (true)
          return ;
        if (args.length != 1) {
            System.out.println("Please supply a file name");
            System.exit(1);
        }
        FileInputStream in = new FileInputStream(args[0]);
        FileOutputStream out = new FileOutputStream(args[0] + ".enc");
        CryptoUtil.encrypt(in, out);
    }
}
