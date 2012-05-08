/* $Name:  $ */
/* $Id: SimpleStringEncryptor.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;



public final class SimpleStringEncryptor implements StringEncryptor{

	private static final LogService logService = new LogService(SimpleStringEncryptor.class);
	
	private final String chars;
	private final String encChars;

	
	public SimpleStringEncryptor() {

		chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		encChars = new StringBuffer(chars).reverse().toString();
	}

	/**
	 * @param uid
	 *            A valid unique userid
	 * @return java.lang.String The unique uid
	 * @exception java.lang.Exception
	 *                If there is an issue with the parameter or error
	 *                encrypting
	 */
	public String encrypt(String uid) {
		if (uid == null) {
			logService.error("null passed into encrypt method");
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (byte b : uid.getBytes()) {
			char c = (char) b;
			int index = chars.indexOf(c);
			if (index >= 0) {
				sb.append(encChars.charAt(index));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * This method decrypts an encrypted user id passed in.
	 * 
	 * @param encrypted_uid
	 *            An encryped user id
	 * @return java.lang.String The decrypted emplid value for the code passed
	 *         in
	 * 
	 */
	public String decrypt(String encrypted_uid) {
		if (encrypted_uid == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (byte b : encrypted_uid.getBytes()) {
			char c = (char) b;
			int index = encChars.indexOf(c);
			if (index >= 0) {
				sb.append(chars.charAt(index));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
