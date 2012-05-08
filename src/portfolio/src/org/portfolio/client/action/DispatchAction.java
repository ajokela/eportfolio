/* $Name:  $ */
/* $Id: DispatchAction.java,v 1.10 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Same thing as DispatchAction in Struts. See
 * http://struts.apache.org/1.3.8/apidocs/org/apache/struts/actions/DispatchAction.html
 * <p />
 * Default mapping param is 'method'. Default method is 'view'.
 * 
 * @author Matt Sheehan
 * 
 */
public class DispatchAction extends BaseAction {

    private static final String DEFAULT_METHOD_NAME = "view";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DefaultMethod {
    }

    /**
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String parameterName = mapping.getParameter();
        if (isEmpty(parameterName)) {
            parameterName = "method";
        }
        
        String methodName = request.getParameter(parameterName);
        
        request.setAttribute("guideFound", false);
        request.setAttribute("className", getClass().toString());
        request.setAttribute("methodName", methodName);
        
        
        Method method;
        if (!isEmpty(methodName)) {
            // method name explicitly given
            method = getClass().getMethod(
                    methodName,
                    ActionMapping.class,
                    ActionForm.class,
                    HttpServletRequest.class,
                    HttpServletResponse.class);
        } else {
            // use default method
            method = getDefaultAnnotatedMethod();
        }
        if (method == null) {
            // no method param and no annotated default, use DEFAULT_METHOD_NAME
            method = getClass().getMethod(
                    DEFAULT_METHOD_NAME,
                    ActionMapping.class,
                    ActionForm.class,
                    HttpServletRequest.class,
                    HttpServletResponse.class);
        }

        try {
            return (ActionForward) method.invoke(this, mapping, form, request, response);
        } catch (InvocationTargetException e) {
            // This can be hard to debug, so throwing the cause
            if (e.getCause() != null && e.getCause() instanceof Exception) {
                throw (Exception) e.getCause();
            }
            throw e;
        }
    }

    private Method getDefaultAnnotatedMethod() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(DefaultMethod.class) != null) {
                return method;
            }
        }
        return null;
    }
}
