/* $Name:  $ */
/* $Id: FileElement.java,v 1.9 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.model;

import java.io.IOException;
import java.io.InputStream;

public interface FileElement extends ElementDataObject {

    String getFileName();

    void setFileName(String fileName);
    void setNameChanged();
    
    String getMimeType();

    int getWidth();

    int getHeight();

    void setWidth(int width);

    void setHeight(int height);

    InputStream getFile() throws IOException;

    long getSize();
    
    String getFormattedSize();
    
    void setSize(long size);
    
    boolean isImage();
    
    String getSHA2();
    
    void setSHA2(String sha2);
    
}
