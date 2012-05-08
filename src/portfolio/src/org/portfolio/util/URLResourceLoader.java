/* $Name:  $ */
/* $Id: URLResourceLoader.java,v 1.8 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.util;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import java.net.URL;

import org.apache.velocity.util.StringUtils;

import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import org.apache.velocity.exception.ResourceNotFoundException;

import org.apache.commons.collections.ExtendedProperties;

public class URLResourceLoader extends ResourceLoader {
    /**
     * The paths to search for templates.
     */
    private Vector<String> paths = null;

    /**
     * Used to map the path that a template was found on
     * so that we can properly check the modification
     * times of the files.
     */
    private Hashtable<String, String> templatePaths = new Hashtable<String, String>();

    @SuppressWarnings("unchecked")
	public void init(ExtendedProperties configuration) {
        // rsvc.info("URLResourceLoader : initialization starting.");

        paths = configuration.getVector("path");

        /*
         *  lets tell people what paths we will be using
         */

        // int sz = paths.size();

        /*
        for (int i = 0; i < sz; i++) {
            rsvc.info("URLResourceLoader : adding path '" + (String) paths.get(i) + "'");
        }

        rsvc.info("URLResourceLoader : initialization complete.");
    	*/
    }

    /**
     * Get an InputStream so that the Runtime can build a
     * template with it.
     *
     * @param name name of template to get
     * @return InputStream containing the template
     * @throws ResourceNotFoundException if template not found
     *         in the file template path.
     */
    public synchronized InputStream getResourceStream(String templateName)
            throws ResourceNotFoundException {
        String template = null;
        int size = paths.size();

        for (int i = 0; i < size; i++) {
            String path = (String) paths.get(i);

            /*
             * Make sure we have a valid templateName.
             */
            if (templateName == null || templateName.length() == 0) {
                /*
                 * If we don't get a properly formed templateName
                 * then there's not much we can do. So
                 * we'll forget about trying to search
                 * any more paths for the template.
                 */
                throw new ResourceNotFoundException(
                        "Need to specify a file name or file path!");
            }

            template = StringUtils.normalizePath(templateName);
            if (template == null || template.length() == 0) {
            	
                String msg = "URL resource error : argument " + template +
                        " contains .. and may be trying to access " +
                        "content outside of template root.  Rejected.";

                // rsvc.error("URLResourceLoader : " + msg);

                throw new ResourceNotFoundException(msg);
            }

            /*
             *  if a / leads off, then just nip that :)
             */
            if (template.startsWith("/")) {
                template = template.substring(1);
            }

            try {
                URL url = new URL(path + "/" + template);

                InputStream inputStream = url.openStream();

                if (inputStream != null) {
                    /*
                     * Store the path that this template came
                     * from so that we can check its modification
                     * time.
                     */

                    templatePaths.put(templateName, path);
                    return inputStream;
                }
            } catch (Exception e) {
            }
        }

        /*
         * We have now searched all the paths for
         * templates and we didn't find anything so
         * throw an exception.
         */
        String msg = "URLResourceLoader Error: cannot find resource " +
                template;

        throw new ResourceNotFoundException(msg);
    }

    /**
     * How to keep track of all the modified times
     * across the paths.
     */
    public boolean isSourceModified(Resource resource) {
        return false;
    }

    public long getLastModified(Resource resource) {
        return 0;
    }
}
