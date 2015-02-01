package com.daphne.dbmdl.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.daphne.dbmdl.core.Constant;
import com.daphne.dbmdl.core.DutiesChain;
import com.daphne.dbmdl.exception.MdlException;

public class EncryptUtil {
	static MessageDigest messagedigest = null;

	public static byte[] encrypt(String content, String password) {
		try {
			// KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128, new SecureRandom(password.getBytes()));
			// SecretKey secretKey = kgen.generateKey();
			// byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(password.getBytes("utf-8"),
					"AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 * @throws MdlException 
	 */
	public static String decrypt(byte[] content, String password) throws MdlException {
		try {
			// KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128, new SecureRandom(password.getBytes()));
			// SecretKey secretKey = kgen.generateKey();
			// byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(password.getBytes("utf-8"),
					"AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return new String(result); // 加密
		} catch (Exception e) {
			throw new MdlException(DutiesChain.FAILURE,"decrypt failure",e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 * @throws MdlException 
	 */
	public static String decrypt(String content, String password) throws MdlException {
		byte[] bs = parseHexStr2Byte(content);
		return decrypt(bs, password);

	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			Integer.toBinaryString(buf[i]);
			Integer.toBinaryString(buf[i] & 0xFF);

			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 * @throws MdlException 
	 */
	public static byte[] parseHexStr2Byte(String hexStr) throws MdlException {
		try{
			if (hexStr.length() < 1)
				return null;
			byte[] result = new byte[hexStr.length() / 2];
			for (int i = 0; i < hexStr.length() / 2; i++) {
				int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
				int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
						16);
				result[i] = (byte) (high * 16 + low);
			}
			return result;
		}catch(Exception e){
			throw new MdlException(DutiesChain.FAILURE,"",e);
		}
		
		
	}

	/**
	 * 生成字节的md5校验码
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getMD5String(byte[] bytes) {

		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messagedigest.reset();
		messagedigest.update(bytes);
		return parseByte2HexStr(messagedigest.digest());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(parseByte2HexStr(encrypt("dpos",Constant.Key)));
	}
}
