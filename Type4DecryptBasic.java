import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class Type4DecryptBasic {
    static String mSessionKey = ""; //input1(DummyKey)
    public static void main(String args[]) throws Exception {
        File input_file = new File(""); //input2 (Encrypted File)
        File output_file = new File(""); //input3
        InputStream decrypted_inputStream = decryptStream(new FileInputStream(input_file));

        FileOutputStream fo = new FileOutputStream(output_file);
        byte[] v2_1 = new byte[0x400];
        while(true){
            int v5 = decrypted_inputStream.read(v2_1, 0,0x400);
            if(v5 == -1){ break; }
            fo.write(v2_1, 0, v5);
        }
        decrypted_inputStream.close();
        fo.close();
    }
    public static InputStream decryptStream(InputStream inputStream) throws Exception {
        SecretKeySpec generateSHA256SecretKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] bArr = new byte[cipher.getBlockSize()];
        inputStream.read(bArr);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);

        generateSHA256SecretKey = generateSHA256SecretKey();


        cipher.init(2, generateSHA256SecretKey, ivParameterSpec);
        return new CipherInputStream(inputStream, cipher);
    }
    public static SecretKeySpec generateSHA256SecretKey() throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(mSessionKey.getBytes("UTF-8"));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return new SecretKeySpec(bArr, "AES");
    }
}
