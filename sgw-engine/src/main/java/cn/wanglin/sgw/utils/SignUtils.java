package cn.wanglin.sgw.utils;

import cn.wanglin.sgw.exception.SignatureException;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class SignUtils {
    public static String sha256(String text,String charset) throws SignatureException{
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(Charset.forName(charset)));
            byte[] digest = md.digest();
            return String.format("%064x", new BigInteger(1, digest));
        }catch (Exception e){
            throw new SignatureException(e.getMessage());
        }
    }
    public static String md5(String text,String charset) throws SignatureException{
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes(Charset.forName(charset)));
            byte[] digest = md.digest();
            return String.format("%064x", new BigInteger(1, digest));
        }catch (Exception e){

            throw new SignatureException(e.getMessage());
        }
    }
}
