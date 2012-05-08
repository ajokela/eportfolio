/* $Name:  $ */
/* $Id: UploadProgressListener.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.multipart;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;

/**
 * http://forum.springsource.org/showthread.php?t=32367
 */
public class UploadProgressListener implements ProgressListener {
    private long bytesRead = 0;
    private long contentLength = 0;
    private boolean multipartFinished = false;

    public UploadProgressListener(HttpServletRequest request) {
        request.getSession().setAttribute("ProgressListener", this);
    }

    public void update(long bytesRead, long contentLength, int items) {
        this.bytesRead = bytesRead;
        this.contentLength = contentLength;
    }

    public void setMultipartFinished() {
        this.multipartFinished = true;
    }

    public boolean isFinished() {
        return multipartFinished;
    }

    public int getPercentDone() {
        if (contentLength == -1) {
            // ensure we never reach 100% but show progress
            return (int) Math.abs(bytesRead * 100.0 / (bytesRead + 10000));
        }
        return (int) Math.abs(bytesRead * 100.0 / contentLength);
    }

    public long getBytesRead() {
        return bytesRead;
    }

    public long getContentLength() {
        return contentLength;
    }
}
