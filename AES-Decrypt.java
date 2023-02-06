
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


public class Decrypt {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] inputByte = toBytes("", 16);
        byte[] inputByte_decoded = Base64.getMimeDecoder().decode(inputByte);
        //print_byte(inputByte_decoded);

        Key key = generateKey("AES", toBytes("696d697373796f7568616e6765656e61", 16));
        byte[] iv = toBytes("26c7d1d26c142de0a3b82f7e8f90860a", 16);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);


        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, key, ivParameterSpec);
        byte[] decrypt = cipher.doFinal(inputByte_decoded, 0, inputByte_decoded.length);

        print_byte(decrypt);
    }
    public static void print_byte(byte[] decrypt){
        StringBuilder builder = new StringBuilder();

        //print
        for (byte data : decrypt) {
            builder.append(String.format("%02X ", data));
        }
        System.out.println(builder.toString());
    }
    public static Key generateKey(String algorithm, byte[] keyData) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        if (!"DES".equals(algorithm)) {
            if ("DESede".equals(algorithm) || "TripleDES".equals(algorithm)) {
                KeySpec keySpec = new DESedeKeySpec(keyData);
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
                return secretKeyFactory.generateSecret(keySpec);
            }
            SecretKeySpec keySpec2 = new SecretKeySpec(keyData, algorithm);
            return keySpec2;
        }
        KeySpec keySpec3 = new DESKeySpec(keyData);
        SecretKeyFactory secretKeyFactory2 = SecretKeyFactory.getInstance(algorithm);
        return secretKeyFactory2.generateSecret(keySpec3);
    }
    public static byte[] toBytes(String digits, int radix) throws IllegalArgumentException, NumberFormatException {
        if (digits == null) {
            return null;
        }
        if (radix != 16 && radix != 10 && radix != 8) {
            throw new IllegalArgumentException("For input radix: \"" + radix + "\"");
        }
        int divLen = radix == 16 ? 2 : 3;
        int length = digits.length();
        if (length % divLen == 1) {
            throw new IllegalArgumentException("For input string: \"" + digits + "\"");
        }
        int length2 = length / divLen;
        byte[] bytes = new byte[length2];
        for (int i = 0; i < length2; i++) {
            int index = i * divLen;
            bytes[i] = (byte) Short.parseShort(digits.substring(index, index + divLen), radix);
        }
        return bytes;
    }
}