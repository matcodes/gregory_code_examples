
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ShaPasswordEncoder {

    private final static String HEX = "0123456789ABCDEF";
    private final String algorithm;
    
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }
    
    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }       

    public ShaPasswordEncoder() {
        this(1);
    }

    public ShaPasswordEncoder(int strength) {
        this("SHA-" + strength);
    }

    public ShaPasswordEncoder(String algorithm) throws IllegalArgumentException {
        this.algorithm = algorithm;
        //Validity Check
        getMessageDigest();
    }

    protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
	    return "--" + salt + "--" + password + "--";
    }

    public String encodePassword(String rawPass, Object salt) {
        String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
        MessageDigest messageDigest = getMessageDigest();
        byte[] digest = messageDigest.digest(saltedPass.getBytes());
        return new String(toHex(digest));
    }

    protected final MessageDigest getMessageDigest() throws IllegalArgumentException {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
        }
    }

    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        String pass1 = "" + encPass;
        String pass2 = encodePassword(rawPass, salt);
        return pass1.equals(pass2);
    }

    public String getAlgorithm() {
        return algorithm;
    }
    
    public static void main(String[] args) {
      ShaPasswordEncoder enc = new ShaPasswordEncoder(1);
      System.out.println(enc.encodePassword("complex pas5word", "12344"));  
    }
}
