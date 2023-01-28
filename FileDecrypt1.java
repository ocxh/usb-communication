import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class FileDecrypt1 {
    static String mSessionKey; //input1
    static byte[] mSalt; //input2
    static int mSecurityLevel = 0; //input3
    public static void main(String[] args) throws Exception {
        File input_file = new File(""); //input4
        File output_file = new File(""); //input5

        InputStream inputStream = new FileInputStream(input_file);
        InputStream outputStream = decryptStream(inputStream);

        OutputStream outStream = new FileOutputStream(output_file);

        byte[] buf = new byte[1024];
        int len = 0;

        while ((len = inputStream.read(buf)) > 0){
            outStream.write(buf, 0, len);
        }
        outputStream.close();

    }
    public static InputStream decryptStream(InputStream inputStream) throws Exception {
        SecretKeySpec generateSHA256SecretKey;
        Cipher mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] bArr = new byte[mCipher.getBlockSize()];

        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        if (mSecurityLevel == 1) {
            mSalt = new byte[16];
            generateSHA256SecretKey = generatePBKDF2SecretKey();
        } else {
            generateSHA256SecretKey = generateSHA256SecretKey();
        }
        mCipher.init(2, generateSHA256SecretKey, ivParameterSpec);
        return new CipherInputStream(inputStream, mCipher);
    }

    public static SecretKeySpec generateSHA256SecretKey() throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(mSessionKey.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, bArr.length);
        return new SecretKeySpec(bArr, "AES");
    }
    public static SecretKeySpec generatePBKDF2SecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(mSessionKey.toCharArray(), mSalt, 1000, 256)).getEncoded(), "AES");
    }
    public static byte[] generateEncryptSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        mSalt = new byte[16];
        secureRandom.nextBytes(mSalt);
        return mSalt;
    }
}
