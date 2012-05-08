/* $Name:  $ */
/* $Id: PortfolioXMLWriter.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

import org.dom4j.io.XMLWriter;

import java.io.Writer;

/**
 * This class extends dom4j XMLWriter so it can escape characters but will avoid escaping &lt; and &gt; symbols. 
 * In WYSIWYG editor text fields HTML markup is included in the data. Those markup needs to be converted to FO
 * equivalent in order to generate PDF of presentation.
 *
 * The only method it overrides is escapeElementEntities(String).
 */

public class PortfolioXMLWriter extends XMLWriter {


     public PortfolioXMLWriter(Writer writer) {
        super(writer);
    }


    protected String escapeElementEntities(String text) {
        StringBuffer buffer = new StringBuffer();
        char[] block = null;
        int i;
        int last = 0;
        int size = text.length();

        for (i = 0; i < size; i++) {
            String entity = null;
            char c = text.charAt(i);

            switch (c) {
                case '&':
                    entity = "&amp;";

                    break;

                case '\t':
                case '\n':
                case '\r':

                    // don't encode standard whitespace characters
                    if (preserve) {
                        entity = String.valueOf(c);
                    }

                    break;

                default:

                    if ((c < 32) || shouldEncodeChar(c)) {
                        entity = "&#" + (int) c + ";";
                    }

                    break;
            }

            if (entity != null) {
                if (block == null) {
                    block = text.toCharArray();
                }

                buffer.append(block, last, i - last);
                buffer.append(entity);
                last = i + 1;
            }
        }

        if (last == 0) {
            return text;
        }

        if (last < size) {
            if (block == null) {
                block = text.toCharArray();
            }

            buffer.append(block, last, i - last);
        }

        String answer = buffer.toString();
        buffer.setLength(0);

        return answer;
    }


}
