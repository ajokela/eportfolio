/* $Name:  $ */
/* $Id: FileSizeUtil.java,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;
import java.util.Random;

public class FileUtil {

    static long KILOBYTE = 1024L;
    static long MEGABYTE = 1024L * 1024L;
    static long GIGABYTE = 1024L * 1024L * 1024L;

    private static final char[] symbols = new char[36];

    static {
      for (int idx = 0; idx < 10; ++idx)
        symbols[idx] = (char) ('0' + idx);
      for (int idx = 10; idx < 36; ++idx)
        symbols[idx] = (char) ('a' + idx - 10);
    }
    
    public static String format(long numBytes) {
        if (numBytes > 10 * GIGABYTE) {
            return ((int) (numBytes / GIGABYTE)) + " GB";
        }
        if (numBytes > 10 * MEGABYTE) {
            return ((int) (numBytes / MEGABYTE)) + " MB";
        }
        if (numBytes > 10 * KILOBYTE) {
            return ((int) (numBytes / KILOBYTE)) + " KB";
        }
        return numBytes + " B";
    }
    
    public static String randomFileName(String ext) {
    	
    	RandomString rs = new RandomString(64);
    	
    	return rs.nextString() + "." + ext;
    }
    
    private static class RandomString
    {

      private final Random random = new Random();

      private final char[] buf;

      public RandomString(int length)
      {
        if (length < 1)
          throw new IllegalArgumentException("length < 1: " + length);
        buf = new char[length];
      }

      public String nextString()
      {
        for (int idx = 0; idx < buf.length; ++idx) 
          buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
      }

    }
    
}
