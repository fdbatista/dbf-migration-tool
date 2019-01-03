package classes.security;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Hex;

public class Security {

    private static Cipher cipher;
    private static final String ivHexString = "FDB0346BD97ECAF30245FB32617E7A60";

    public static void init() throws Exception {
        if (cipher == null)
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }
    
    public String getHash(String inputVal) throws Exception {
        MessageDigest myDigest = MessageDigest.getInstance("SHA-256");
        myDigest.update(inputVal.getBytes());
        byte[] dataBytes = myDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dataBytes.length; i++) {
            sb.append(Integer.toString((dataBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(ivHexString.toCharArray()));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }
    
    public static String encryptToHex(String plainText, SecretKey secretKey) throws Exception {
        String encryptedText = encrypt(plainText, secretKey);
        byte[] bytes = DatatypeConverter.parseBase64Binary(encryptedText);
        char[] hexString = Hex.encodeHex(bytes);
        String strHex = String.valueOf(hexString).toUpperCase();
        return strHex;
    }
    
    public static String encryptToHex(String plainText, String secretKeyString) throws Exception {
        String encryptedText = encrypt(plainText, secretKeyString);
        byte[] bytes = DatatypeConverter.parseBase64Binary(encryptedText);
        char[] hexString = Hex.encodeHex(bytes);
        String strHex = String.valueOf(hexString).toUpperCase();
        return strHex;
    }
    
    public static String encrypt(String plainText, String secretKeyString) throws Exception {
        init();
        byte[] plainTextByte = plainText.getBytes();
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(ivHexString.toCharArray()));
        SecretKey secretKey = new SecretKeySpec(secretKeyString.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        init();
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(ivHexString.toCharArray()));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }
    
    public static String decrypt(String encryptedText, String secretKeyString) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(ivHexString.toCharArray()));
        SecretKey secretKey = new SecretKeySpec(secretKeyString.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

}
