/* $Name:  $ */
/* $Id: StringEncryptor.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

/**
 * Class for encryption / decryption of Strings. So far it is used mainly for
 * putting encrypted userIds in URLs.
 * 
 * @author Matt Sheehan
 * 
 */
public interface StringEncryptor {

	/**
	 * Encrypt the String.
	 * 
	 * @param string the String to encrypt
	 * @return the encrypted String
	 */
	public String encrypt(String string);

	/**
	 * Decrypt the String.
	 * 
	 * @param encryptedString the encrypted String
	 * @return the orginal String value
	 */
	public String decrypt(String encryptedString);

}
