package com.ldp.datahub.util;

import org.apache.commons.codec.binary.Base64;

public class CodecUtil {
	
	public static String basic64Encode(String code) {

		return Base64.encodeBase64String(code.getBytes());
	}

	public static String basic64Decode(String code) {
		return new String(Base64.decodeBase64(code));
	}
	
	public static void main(String[] args) {
		
		String loginName="liuxy10@asiainfo.com";
//		String nameEncode = encoder.encode(loginName.getBytes());
//		System.err.println(nameEncode);
//		String s = new String(Base64.encodeBase64(loginName))
		System.err.println(Base64.encodeBase64String(loginName.getBytes()));
	}
}
