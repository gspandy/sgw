package cn.wanglin.sgw.exchange.utils;

import cn.wanglin.sgw.exchange.exception.CryptException;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/** */

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 * @author 苏扬
 * @version 1.0
 * @date 2015-6-26
 */
public class RSAUtils {

    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 53;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 64;

    /** */
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @
     */
    public static Map<String, Object> genKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(512);
            KeyPair             keyPair    = keyPairGen.generateKeyPair();
            RSAPublicKey        publicKey  = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey       privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap     = new HashMap<String, Object>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            throw new CryptException(e.getMessage());
        }
    }

    /** */
    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @
     */
    public static String sign(byte[] data, String privateKey) {
        try {
            byte[]              keyBytes     = Base64Utils.decode(privateKey.getBytes());
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory          keyFactory   = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey          privateK     = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature           signature    = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateK);
            signature.update(data);
            return Base64Utils.encode(signature.sign());
        } catch (Exception e) {
            throw new CryptException(e.getMessage());
        }
    }

    /** */
    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @
     */
    public static boolean verify(byte[] data, String publicKey, String sign) {
        try {
            byte[]             keyBytes   = Base64Utils.decode(publicKey.getBytes());
            X509EncodedKeySpec keySpec    = new X509EncodedKeySpec(keyBytes);
            KeyFactory         keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey          publicK    = keyFactory.generatePublic(keySpec);
            Signature          signature  = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicK);
            signature.update(data);
            return signature.verify(Base64Utils.decode(sign.getBytes()));
        } catch (Exception e) {
            throw new CryptException(e.getMessage());
        }
    }

    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) {
        try {
            byte[]              keyBytes     = Base64Utils.decode(privateKey.getBytes());
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory          keyFactory   = KeyFactory.getInstance(KEY_ALGORITHM);
            Key                 privateK     = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher              cipher       = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            int                   inputLen = encryptedData.length;
            ByteArrayOutputStream out      = new ByteArrayOutputStream();
            int                   offSet   = 0;
            byte[]                cache;
            int                   i        = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            throw new CryptException(e.getMessage());
        }
    }

    /** */
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
        try {
            byte[]             keyBytes    = Base64Utils.decode(publicKey.getBytes());
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory         keyFactory  = KeyFactory.getInstance(KEY_ALGORITHM);
            Key                publicK     = keyFactory.generatePublic(x509KeySpec);
            Cipher             cipher      = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicK);
            int                   inputLen = encryptedData.length;
            ByteArrayOutputStream out      = new ByteArrayOutputStream();
            int                   offSet   = 0;
            byte[]                cache;
            int                   i        = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            throw new CryptException(e.getMessage());
        }
    }

    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) {
        try {
            byte[]             keyBytes    = Base64Utils.decode(publicKey.getBytes());
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory         keyFactory  = KeyFactory.getInstance(KEY_ALGORITHM);
            Key                publicK     = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            int                   inputLen = data.length;
            ByteArrayOutputStream out      = new ByteArrayOutputStream();
            int                   offSet   = 0;
            byte[]                cache;
            int                   i        = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            throw new CryptException(e.getMessage());
        }
    }

    /** */
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        try {
            byte[]              keyBytes     = Base64Utils.decode(privateKey.getBytes());
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory          keyFactory   = KeyFactory.getInstance(KEY_ALGORITHM);
            Key                 privateK     = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher              cipher       = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateK);
            int                   inputLen = data.length;
            ByteArrayOutputStream out      = new ByteArrayOutputStream();
            int                   offSet   = 0;
            byte[]                cache;
            int                   i        = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            throw new CryptException(e.getMessage());
        }
    }

    /** */
    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /** */
    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * DESCRIPTION:
     *
     * @param args
     * @return
     * @author suyang
     * @date 2015年12月30日
     * main 方法
     */
    public static void main(String[] args) {

//    	PaymentServiceImpl sss = new PaymentServiceImpl();
//    	QueryLoanDetails queryLoanDetails = new QueryLoanDetails();
//    	queryLoanDetails.setApplSeq("941846");
//    	sss.queryLoanDetails(queryLoanDetails);


//    	MeiChuangNoticeInfo ss = new MeiChuangNoticeInfo();
//        ss.setTransNo("1111");
//    	ss.setOrderNo("2222");
//    	ss.setUserId("3333");
//    	ss.setCreditNo("4444");	
//    	ss.setApplystatus("27");
//    	ss.setApplyMessage("通过");
//    	ss.setName("kevin"); 
//    	ss.setIdnumber("370682198909783612");
//    	ss.setPhone("18660839714");	
//    	ss.setOrdtotalloan("8000.00");
//    	
//    	PaymentServiceImpl psm = new PaymentServiceImpl();
//    	String result = psm.mcCheckNotice(ss);
        //mcCheckNotice


        //XiMuTask xm = new XiMuTask();
        //xm.run();
        try {
            Map    map        = genKeyPair();
            String publicKey  = getPublicKey(map);
            String privateKey = getPrivateKey(map);
//			String publicKey ="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMsAEWQepQLdo3mhgw3mLU00n/ldDd4knbq01bH89XnE5RITn+qZBAkHow5jgwH2T5ve2KJzkrHD6JbDDQtRO0UCAwEAAQ==";
//			String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAywARZB6lAt2jeaGDDeYtTTSf+V0N3iSdurTVsfz1ecTlEhOf6pkECQejDmODAfZPm97YonOSscPolsMNC1E7RQIDAQABAkBdolFjQfNQ6BSgZMxDW/lnVC+54J8l6PPMC99JsukzWbjvCqCxMY2/9LIV7W1OpzTp0OcPH+8FdI+GTrQqaL59AiEA7NUfjdHVvqgjWBBLxvYmk9KSVol5eQPE2LL9h4OVMXsCIQDbbft7qRNcISk+oacS109IvS6FiR6QtCxt01ci/v3KPwIgAsWPw/ojtUKEf2BZcq+ozewV+FtNh6QKprqZiv94PIcCICVYeJ65TfQ2KKDiPl80ieflWs+bYLpcD9u5RnFMWMFdAiEAkhXiwm4ltINMTcj82/fl0BwujBb0jybXfVnKNHH43S0=";
            System.out.println("公钥----" + publicKey);
            System.out.println("私钥----" + privateKey);
            String abc = "haiercashpay";
            String sss = "";
            //String sss ="vHaXHgAm+ihC6fmdLp4Ynbdyy7GOo6OAv1bvjjgBFBov4/XrB40gEIscTIaf/Yh54gecQVcAZbVfVDOiFDGBkg==";
            abc = Base64Utils.encode(encryptByPrivateKey(abc.getBytes(), privateKey));
            System.out.println("加密后的数据：" + abc);
            sss = new String(decryptByPublicKey(Base64Utils.decode(abc.getBytes()), publicKey));
            System.out.println("解密后数据：" + sss);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

} 
