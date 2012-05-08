/* $Name:  $ */
/* $Id: ElUtil.java,v 1.19 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Matt Sheehan
 */
@Component
public class ElUtil {

    private static StringEncryptor stringEncryptor;

    @Autowired(required = true)
    public void setStringEncryptor(StringEncryptor stringEncryptor) {
        ElUtil.stringEncryptor = stringEncryptor;
    }

    /**
     * Normally you use [] or . to get the value of an EL array. This doesn't work, though, if the value is a number. Then it thinks its the
     * array index. You could quote the value, but that doesn't work if you need to get it from another EL var. Example:
     * <p/>
     * ${paramValues.publicTags[publicTag.standardId]}
     * <p/>
     * Maybe there's another way but I couldn't figure it out.
     * 
     * @param array
     * @param value
     * @return
     */
    public static boolean contains(String[] array, String value) {
        if (array != null) {
            for (String string : array) {
                if (string.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Wraps the string by inserting line breaks.
     * 
     * @param src the String to wrap.
     * @param columns the number of characters to allow on each line.
     * @return the string with line breaks.
     */
    public static String wrap(String src, int columns) {
        String lineSeparator = "<br/>";
        // Null or blank string should return an empty ("") string
        if (src == null) {
            return "";
        }
        String src1 = src.trim();
        if (src1.length() > columns) {
            StringBuffer out = new StringBuffer();
            while (src1.length() > columns) {
                int endIndex = columns;
                if (src1.charAt(columns - 1) != ' ' && src1.lastIndexOf(' ', columns) > -1) {
                    endIndex = src1.lastIndexOf(' ', columns);
                }
                out.append(src1.substring(0, endIndex).trim());
                out.append(lineSeparator);
                src1 = src1.substring(endIndex).trim();
            }
            out.append(src1);
            return out.toString();
        } else {
            return src;
        }
    }

    /**
     * Highlights matching patterns in the given string by wrapping a span around them with a class of 'highlight'.
     * 
     * @param s the string to inspect.
     * @param pattern the match pattern.
     * @return the string with matches highlighted.
     */
    public static String highlightTextMatch(String s, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "<span class=\"highlight\">" + m.group() + "</span>");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * Encrypts the string.
     * 
     * @param s the string to encrypt.
     * @return the encrypted String.
     */
    public static String encrypt(String s) {
        try {
            return stringEncryptor.encrypt(s);
        } catch (Exception e) {
            throw new RuntimeException("couldn't encrypt string: " + s, e);
        }
    }

    /**
     * Encodes the string for a URL.
     * 
     * @param s the string to encode.
     * @return the encoded string.
     */
    public static String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported", e);
        }
    }

    /**
     * Joins bean property values with the given delimiter.
     * 
     * @param c the collection.
     * @param propName the name of the bean property.
     * @param delim the delimiter.
     * @return a string of the bean values joined with the delimiter.
     */
    public static String joinProperty(Collection<?> c, String propName, String delim) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<?> iterator = c.iterator(); iterator.hasNext();) {
            Object object = iterator.next();
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(object.getClass(), propName);
            try {
                Object result = propertyDescriptor.getReadMethod().invoke(object);
                sb.append(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (iterator.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * Escapes &amp;, &lt;, &gt;, ', and " as unicode. This would be necessary when displaying HTML that calls a JavaScript function like
     * this: <code>callFunction('${argument}');</code> If ${argument} contains a single quote or an HTML-escaped single quote, the JavaScript will break.
     * 
     * @param arg the String to escape
     * @return the escaped String
     */
    public static String encodeJavaScriptArgument(String arg) {
        return arg
                .replaceAll("&", "\\\\u0026")
                .replaceAll("<", "\\\\u003C")
                .replaceAll(">", "\\\\u003E")
                .replaceAll("'", "\\\\u0027")
                .replaceAll("\"", "\\\\u0022");
    }

    /**
     * If you want to load a dynamic resource and prevent caching you can create a url like this: dynamic.do?t=${currentTimeMillis}
     * 
     * @return the current time in milliseconds.
     */
    public static String currentTimeMillis() {
        return "" + System.currentTimeMillis();
    }

    /**
     * Filter out cross-site scripting attacks from HTML.
     * 
     * @return filtered output.
     */
    public static String xssFilter(String html) {
        return new XssFilter().filter(html);
    }

    /**
     * Creates a random id for an element.
     */
    public static String randomId() {
        return "" + new Random().nextInt(1000000);
    }
}
