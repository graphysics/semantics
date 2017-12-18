package semantics.java.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author ysc
 */
public class PKIUtils {
    private static final Logger log = LoggerFactory.getLogger(PKIUtils.class);
       
    private PKIUtils(){}
    /**
     * 
     * ��֤���˽Կǩ��
     * @param in ֤���
     * @param storePassword ֤�������
     * @param keyPassword ֤������
     * @param key Կ����
     * @param data ��ǩ������
     * @return ǩ��
     */
    public static byte[] signature(InputStream in, String storePassword, String keyPassword, String key, byte[] data) {
        try {
            // ��ȡ֤��˽Կ
            PrivateKey privateKey = getPrivateKey(in, storePassword, keyPassword, key);
            Signature signet = Signature.getInstance("MD5withRSA");
            signet.initSign(privateKey);
            signet.update(data);
            byte[] signed = signet.sign(); // ����Ϣ������ǩ��
            return signed;
        } catch (Exception ex) {
            log.error("ǩ��ʧ��",ex);
        }
        return null;
    }
    /**
     * ��֤��Ĺ�Կ��֤ǩ��
     * @param in ֤��
     * @param data ԭʼ����
     * @param signatureData ��ԭʼ���ݵ�ǩ��
     * @return 
     */
    public static boolean verifySignature(InputStream in, byte[] data, byte[] signatureData){
        try {
            // ��ȡ֤�鹫Կ
            PublicKey key = getPublicKey(in);
            Signature signet = Signature.getInstance("MD5withRSA");
            signet.initVerify(key);
            signet.update(data);
            boolean result=signet.verify(signatureData);
            return result;
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            log.error("��֤ǩ��ʧ��",ex);
        }
        return false;
    }

    /**
     * ��ȡ֤�鹫Կ
     * @param in ֤��
     * @return ��Կ
     */
    private static PublicKey getPublicKey(InputStream in) {
        try {
            // ��֤��Ĺ�Կ����
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate cert = factory.generateCertificate(in);
            // �õ�֤���ļ�Я���Ĺ�Կ
            PublicKey key = cert.getPublicKey();
            return key;
        } catch (CertificateException ex) {
            log.error("��ȡ֤�鹫Կʧ��",ex);
        }
        return null;
    }

    /**
     * ��������
     * @param key ��Կ��˽Կ
     * @param data ����������
     * @return 
     */
    public static byte[] encrypt(Key key, byte[] data) {
        try {
            // �����㷨��RSA
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // ��ʽִ�м��ܲ���
            byte encryptedData[] = cipher.doFinal(data);
            return encryptedData;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            log.error("��������ʧ��",ex);
        }
        return null;
    }
    /**
     * ��֤��Ĺ�Կ����
     * @param in ֤��
     * @param data ����������
     * @return ����
     */
    public static byte[] encryptWithPublicKey(InputStream in, byte[] data) {
        try {
            // ��ȡ֤�鹫Կ
            PublicKey key = getPublicKey(in);
            
            byte encryptedData[] = encrypt(key,data);
            return encryptedData;
        } catch (Exception ex) {
            log.error("��֤��Ĺ�Կ����ʧ��",ex);
        }
        return null;
    }
    /**
     * ��֤���˽Կ����
     * @param in ֤���
     * @param storePassword ֤�������
     * @param keyPassword ֤������
     * @param key Կ����
     * @param data ����������
     * @return ����
     */
    public static byte[] encryptWithPrivateKey(InputStream in, String storePassword, String keyPassword, String key, byte[] data) {
        try {
            // ��ȡ֤��˽Կ
            PrivateKey privateKey = getPrivateKey(in, storePassword, keyPassword, key);
            
            byte encryptedData[] = encrypt(privateKey,data);
            return encryptedData;
        } catch (Exception ex) {
            log.error("��֤���˽Կ����ʧ��",ex);
        }
        return null;
    }

    /**
     * ��ȡ֤��˽Կ
     * @param in ֤���
     * @param storePassword ֤�������
     * @param keyPassword ֤������
     * @param key Կ����
     * @return ˽Կ
     */
    private static PrivateKey getPrivateKey(InputStream in, String storePassword, String keyPassword, String key) {
        try {
            // ����֤���
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, storePassword.toCharArray());
            // ��ȡ֤��˽Կ
            PrivateKey privateKey = (PrivateKey) ks.getKey(key, keyPassword.toCharArray());
            return privateKey;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException ex) {
            log.error("��ȡ֤��˽Կʧ��",ex);
        }
        return null;
    }

    /**
     * ��������
     * @param key ��Կ��˽Կ
     * @param data ����������
     * @return  ����
     */
    public static byte[] decrypt(Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            // ���ܺ������
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            log.error("��������ʧ��",ex);
        }
        return null;
    }
    /**
     * 
     * ��֤���˽Կ���� 
     * @param in ֤���
     * @param storePassword ֤�������
     * @param keyPassword ֤������
     * @param key Կ����
     * @param data ����������
     * @return ����
     */
    public static byte[] decryptWithPrivateKey(InputStream in, String storePassword, String keyPassword, String key, byte[] data) {
        try {
            // ��ȡ֤��˽Կ
            PrivateKey privateKey = getPrivateKey(in, storePassword, keyPassword, key);
            // ���ܺ������
            byte[] result = decrypt(privateKey,data);
            return result;
        } catch (Exception ex) {
            log.error("��֤���˽Կ����ʧ��",ex);
        }
        return null;
    }
    /**
     * 
     * ��֤��Ĺ�Կ���� 
     * @param in ֤��
     * @param data ����������
     * @return ����
     */
    public static byte[] decryptWithPublicKey(InputStream in, byte[] data) {
        try {
            // ��ȡ֤�鹫Կ
            PublicKey key = getPublicKey(in);
            // ���ܺ������
            byte[] result = decrypt(key,data);
            return result;
        } catch (Exception ex) {
            log.error("��֤��Ĺ�Կ����ʧ��",ex);
        }
        return null;
    }
}
