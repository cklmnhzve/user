package com.ldp.datahub.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class CodecUtil {
	
	public static String basic64Encode(String code) {

		return Base64.encodeBase64String(code.getBytes());
	}

	public static String basic64Decode(String code) {
		return new String(Base64.decodeBase64(code));
	}
	
	public static String MD5(String str) {
		return DigestUtils.md5Hex(str);
	}
	
	public static void main(String[] args) {
		
		String loginName="liuxueying1001@126.com";
//		String nameEncode = encoder.encode(loginName.getBytes());
//		System.err.println(nameEncode);
//		String s = new String(Base64.encodeBase64(loginName))
		System.err.println(Base64.encodeBase64String(loginName.getBytes()));
	}
}
