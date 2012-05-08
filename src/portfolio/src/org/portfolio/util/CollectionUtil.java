/* $Name:  $ */
/* $Id: CollectionUtil.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.portfolio.model.ElementDataObject;

/**
 * @author Matt Sheehan
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * Returns the intersection of the two collections. If both are null, null is returned. If one is null, the other is returned.
     */
    public static <E> Set<E> intersection(Collection<E> a, Collection<E> b) {
        if (a == null && b == null) {
            return null;
        }
        if (a == null) {
            return new HashSet<E>(b);
        }
        if (b == null) {
            return new HashSet<E>(a);
        }
        List<E> result = new ArrayList<E>();
        for (E e : a) {
            if (b.contains(e)) {
                result.add(e);
            }
        }
        return new HashSet<E>(result);
    }

    /**
     * Returns the union of the two collections. If both are null, null is returned. If one is null, the other is returned.
     */
    public static <E> Set<E> union(Collection<E> a, Collection<E> b) {
        if (a == null && b == null) {
            return null;
        }
        if (a == null) {
            return new HashSet<E>(b);
        }
        if (b == null) {
            return new HashSet<E>(a);
        }
        Set<E> result = new HashSet<E>();
        result.addAll(a);
        result.addAll(b);
        return result;
    }

    /**
     * Join the collection into a string with the given delim.
     */
    public static String join(Collection<?> c, String delim) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<?> iterator = c.iterator(); iterator.hasNext();) {
            Object name = iterator.next();
            sb.append(name.toString());
            if (iterator.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    public static <E> List<E> subtract(Collection<E> allLinks, Collection<E> attachedLinks) {
        List<E> result = new ArrayList<E>();
        if (attachedLinks != null) {
            for (E e : allLinks) {
                if (!attachedLinks.contains(e)) {
                    result.add(e);
                }
            }
        }
        return result;
    }
    
    public static <E> List<? extends ElementDataObject> subtract(List<? extends ElementDataObject> allLinks, List<? extends ElementDataObject> attachedLinks) {
        List<ElementDataObject> result = new ArrayList<ElementDataObject>();
        if (attachedLinks != null) {
            for (ElementDataObject e : allLinks) {
                if (!attachedLinks.contains(e)) {
                    result.add(e);
                }
            }
        }
        return result;
    }
}
