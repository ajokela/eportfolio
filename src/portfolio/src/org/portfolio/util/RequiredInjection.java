/* $Name:  $ */
/* $Id: RequiredInjection.java,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for setter methods that require dependency injection.
 * 
 * @author Matt Sheehan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredInjection {
}
