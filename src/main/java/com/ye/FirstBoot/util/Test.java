package com.ye.FirstBoot.util;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Test {
	public static PublicKey publicKey;
	public static PrivateKey privateKey;
	public static String pubStr;
    public static String priStr;
    public static String data ="Hello,加密的世界";
    public static byte[] byteData =null;
    
	public static void main(String[] args) throws Exception {
		 publicKey = RSAUtils.getPublicKeyFromKeyStore("D:\\mydata\\yekey.jks","yeyypz", "JKS",  "www.yypz.com");
		 privateKey = RSAUtils.getPrivateKeyFromKeyStore("D:\\mydata\\yekey.jks","yeyypz", "JKS",  "www.yypz.com");
		 pubStr= Base64Utils.encode(publicKey.getEncoded());
		 priStr= Base64Utils.encode(privateKey.getEncoded());
		 byteData =  data.getBytes();
		 
		 testEncryptAndDecrypt();
		 testSignAndVerify();
	}
	
	public static void testEncryptAndDecrypt() throws Exception {
		byte[] bytes =RSAUtils.encryptByPrivateKey(byteData, priStr);
		System.out.println(new String("after private key encrypt:"+bytes));
		bytes = RSAUtils.decryptByPublicKey(bytes,pubStr);
		System.out.println(new String("after public key decrypt:"+bytes));
	}
	
	public static void testSignAndVerify() throws Exception {
		String signDate =RSAUtils.sign(data.getBytes(), priStr);
		System.out.println(new String("after private key encrypt:"+signDate));
		boolean pass = RSAUtils.verify(byteData, pubStr, signDate);
		System.out.println("verify result:"+pass);
	}
	

}
