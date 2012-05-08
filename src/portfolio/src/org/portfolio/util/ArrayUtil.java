/* $Name:  $ */
/* $Id: ArrayUtil.java,v 1.5 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.util;

/**
 * @author Matt Sheehan
 * 
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    public static int[] stringsToInts(String... strings) {
        int[] ints = null;
        if (strings != null) {
            ints = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                ints[i] = Integer.parseInt(strings[i]);
            }
        }
        return ints;
    }

    public static int calculateAverage(int... ints) {
        int result = 0;
        for (int num : ints) {
            result += num;
        }
        result = Math.round(result / (float) ints.length);
        return result;
    }

    /**
     * Join the array into a string with the given delim.
     */
    public static String join(int[] arr, String delim) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * Join the array into a string with the given delim.
     */
    public static String join(Object[] arr, String delim) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * true if null or size is 0
     */
    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }
}
