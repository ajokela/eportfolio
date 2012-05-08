package org.portfolio.util;

public class HexTools {

	public static String bytesToHex(byte[] mdbytes) {
		
		StringBuffer sb = new StringBuffer();
		
        for (int i = 0; i < mdbytes.length; i++) {
        	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return sb.toString();
	}
        
        
}