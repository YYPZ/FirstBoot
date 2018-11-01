package com.ye.FirstBoot.common.util;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RSATest {
	
	/**
	 * KS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore yekey.jks -destkeystore yekey.jks -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。
	 * 迁移后 使用KeyStore.getInstance(keyType);keyType可以是 PKCS12 或者 JKS,同时可以将文件名改为.pfx后缀 ,未转之前只能是JKS
	 */
	public static PublicKey publicKey;
	public static PrivateKey privateKey;
	public static String pubStr;
	public static String priStr;
	public static String data = "Hello,加密的世界";
	public static byte[] byteData = null;

	public static void main(String[] args) throws Exception {
		
		
		publicKey = RSAUtils.getPublicKeyFromKeyStore("D:\\mydata\\yekey.jks", "yeyypz", "PKCS12", "www.yypz.com");
		privateKey = RSAUtils.getPrivateKeyFromKeyStore("D:\\mydata\\yekey.jks", "yeyypz", "PKCS12", "www.yypz.com");
		//publicKey = RSAUtils.getPublicKeyFromKeyStore("D:\\mydata\\macaupassenterprise.pfx", "macaupass_pg", "PKCS12", "0123456789abcdef");
		//privateKey = RSAUtils.getPrivateKeyFromKeyStore("D:\\mydata\\macaupassenterprise.pfx", "macaupass_pg", "PKCS12", "0123456789abcdef");
		pubStr = Base64Utils.encode(publicKey.getEncoded());
		priStr = Base64Utils.encode(privateKey.getEncoded());
		byteData = data.getBytes();

		testEncryptAndDecrypt();
		testSignAndVerify();
		RSAUtils.getX509CerCate("D:\\mydata\\yekey.cer");
	}

	public static void testEncryptAndDecrypt() throws Exception {
		byte[] bytes = RSAUtils.encryptByPrivateKey(byteData, priStr);
		System.out.println("after private key encrypt:"+new String(bytes));
		bytes = RSAUtils.decryptByPublicKey(bytes, pubStr);
		System.out.println("after public key decrypt:" + new String(bytes));
	}

	public static void testSignAndVerify() throws Exception {
		String signDate = RSAUtils.sign(data.getBytes(), priStr);
		System.out.println(new String("after private key sign:" + signDate));
		boolean pass = RSAUtils.verify(byteData, pubStr, signDate);
		System.out.println("verify result:" + pass);
	}
	
	

}
