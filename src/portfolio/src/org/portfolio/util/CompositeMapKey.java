/* $Name:  $ */
/* $Id: CompositeMapKey.java,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

import java.util.Arrays;

/**
 * Object that can be used as a map key when multiple objects are needed for
 * identification.
 * 
 * @author Matt Sheehan
 */
public class CompositeMapKey {

    private final Object[] args;

    public CompositeMapKey(Object... args) {
        this.args = args;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof CompositeMapKey && Arrays.equals(this.args, ((CompositeMapKey) that).args);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.args);
    }
}
