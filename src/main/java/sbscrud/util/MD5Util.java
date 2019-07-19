package sbscrud.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

public class MD5Util {

    public static String hex(byte[] array) {
        return IntStream.range(0, array.length)
                .map(i -> array[i])
                .mapToObj(b -> Integer.toHexString((b & 0xFF) | 0x100).substring(1,3))
                .reduce((x1, x2) -> x1.concat(x2))
                .orElse("");
    }

    public static String md5Hex(String message) {
        try {
            var md = MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
