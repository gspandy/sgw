package cn.wanglin.sgw.exchange.jd;

import cn.wanglin.sgw.exchange.exception.CryptException;
import cn.wanglin.sgw.exchange.exception.SignatureException;
import cn.wanglin.sgw.exchange.utils.Base64Utils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;

public class JDUtils {

    public static boolean verify(String text, String key, String sign) throws SignatureException {
        String md5Text = DigestUtils.md5Hex(text + key);
        if (md5Text.equalsIgnoreCase(sign)) {
            return true;
        } else {
            throw new SignatureException();
        }
    }


    /**
     * 加密方法
     *
     * @param text    明文
     * @param key     密钥 BASE64
     * @param charset 字符集
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String text, String key, String charset) {
        try {
            byte[]           keyBase64DecodeBytes = Base64Utils.decode(key.getBytes(Charset.forName(charset)));//base64解码key
            DESKeySpec       desKeySpec           = new DESKeySpec(keyBase64DecodeBytes);//前8个字节做为密钥
            SecretKeyFactory keyFactory           = SecretKeyFactory.getInstance("DES");
            SecretKey        secretKey            = keyFactory.generateSecret(desKeySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] textBytes = text.getBytes(charset);//明文UTF-8格式
            byte[] bytes     = cipher.doFinal(textBytes);//DES加密

            return Base64Utils.encode(bytes);
        } catch (Exception e) {
            throw new CryptException(e.getMessage());
        }
    }

    public static String decrypt(String text, String key, String charset)
            throws Exception {
        byte[] keyBase64DecodeBytes = Base64Utils.decode(key.getBytes(Charset.forName(charset)));

        DESKeySpec       desKeySpec = new DESKeySpec(keyBase64DecodeBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey        secretKey  = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] textBytes = Base64Utils.decode(text.getBytes(Charset.forName(charset)));
        byte[] bytes     = cipher.doFinal(textBytes);

        String decryptString = new String(bytes, charset);

        return decryptString;
    }

    public static String toXML(Object obj) {
        return null;
    }


}
