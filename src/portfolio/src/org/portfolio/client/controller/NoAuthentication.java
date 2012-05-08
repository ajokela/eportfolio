/* $Name:  $ */
/* $Id: NoAuthentication.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For Spring MVC Controllers that don't need an authentication check.
 * 
 * @author Matt Sheehan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NoAuthentication {
}
