package com.mystery0.isafe.PublicMethod;

import android.util.Log;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Cryptogram
{
    private static String Algorithm = "DES";

    /**
     * Md5生成密钥key
     */
    private static String MD5(String inStr)
    {
        MessageDigest md5;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e)
        {
            Log.e("error",e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes)
        {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 将指定的数据根据提供的密钥进行加密
     *
     * @param input 需要加密的数据
     * @param key   密钥
     * @return byte[] 加密后的数据
     * @throws Exception
     */
    private static byte[] encryptData(byte[] input, byte[] key) throws Exception
    {
        SecretKey desKey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, desKey);
        return c1.doFinal(input);

    }

    /**
     * 将给定的已加密的数据通过指定的密钥进行解密
     *
     * @param input 待解密的数据
     * @param key   密钥
     * @return byte[] 解密后的数据
     * @throws Exception
     */
    private static byte[] decryptData(byte[] input, byte[] key) throws Exception
    {
        SecretKey desKey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, desKey);
        return c1.doFinal(input);

    }

    /**
     * 字符串转成字节数组.
     *
     * @param hex 要转化的字符串.
     * @return byte[] 返回转化后的字符串.
     */
    private static byte[] hexStringToByte(String hex)
    {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] aChar = hex.toCharArray();
        for (int i = 0; i < len; i++)
        {
            int pos = i * 2;
            result[i] = (byte) (toByte(aChar[pos]) << 4 | toByte(aChar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c)
    {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 字节数组转成字符串.
     *
     * @return 返回转化后的字节数组.
     */
    private static String bytesToHexString(byte[] bArray)
    {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte aBArray : bArray)
        {
            sTemp = Integer.toHexString(0xFF & aBArray);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 获取密钥.
     * @return 返回的密钥.
     * @throws Exception 可能抛出的异常.
     */
    private static byte[] getSecretKey(String id) throws Exception
    {
        byte[] key=hexStringToByte(MD5(id));
        byte[] trueKey=new byte[8];
        System.arraycopy(key, 0, trueKey, 0, trueKey.length);
        return trueKey;
    }

    /**
     * 加密
     *
     * @param info 加密前数据
     * @param id key
     * @return 加密后数据
     * @throws Exception
     */
    public static String JM(String info,String id) throws Exception
    {
        byte[] key = getSecretKey(id);
        return bytesToHexString(encryptData(info.getBytes(), key));
    }

    /**
     * 解密
     *
     * @param info 解密前数据
     * @param id key
     * @return 解密后数据
     * @throws Exception
     */
    public static String JX(String info,String id) throws Exception
    {
        byte[] key = getSecretKey(id);
        return new String(decryptData(hexStringToByte(info), key));
    }
}
