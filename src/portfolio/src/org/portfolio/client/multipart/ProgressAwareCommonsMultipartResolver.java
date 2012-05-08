/* $Name:  $ */
/* $Id: ProgressAwareCommonsMultipartResolver.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.multipart;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * http://forum.springsource.org/showthread.php?t=32367
 */
public class ProgressAwareCommonsMultipartResolver extends CommonsMultipartResolver {
    private static ThreadLocal<UploadProgressListener> progressListener = new ThreadLocal<UploadProgressListener>();

    @Override
    public void cleanupMultipart(MultipartHttpServletRequest request) {
        progressListener.get().setMultipartFinished();
        super.cleanupMultipart(request);
    }

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        FileUpload fileUpload = super.newFileUpload(fileItemFactory);
        fileUpload.setProgressListener(progressListener.get());
        return fileUpload;
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        progressListener.set(new UploadProgressListener(request));
        return super.resolveMultipart(request);
    }
}
