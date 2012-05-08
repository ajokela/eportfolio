/* $Name:  $ */
/* $Id: Configuration.java,v 1.19 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.util;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Configuration {

	private static Properties properties = new Properties();

	/**
	 * returns back the value of the property requested, if the property can't be found or the name
	 * is null, null is returned
	 * 
	 * @param name - name of the property to get, in the form: 'system.server.host-name'
	 * @return - String respresentation of this property, use the type specific gets if you want the
	 *         property back as a certain type
	 */
	static public String get(String name) {
		return properties.getProperty( name );
	}
	
	static public Properties getProperties() {
		return properties;
	}

	static public int getInteger(String name) {
		try {
			return Integer.valueOf( get( name ) ).intValue();
		} catch (NumberFormatException e) {
			throw new RuntimeException( "Configuration could not parse " + get( name )
					+ " into a valid int, check portfolio.xml for errors" );
		}
	}

	static public double getDouble(String name) {
		try {
			return Double.valueOf( get( name ) ).doubleValue();
		} catch (NumberFormatException e) {
			throw new RuntimeException( "Configuration could not parse " + get( name )
					+ " into a valid double, check portfolio.xml for errors" );
		}
	}

	static public float getFloat(String name) {
		try {
			return Float.valueOf( get( name ) ).floatValue();
		} catch (NumberFormatException e) {
			throw new RuntimeException( "Configuration could not parse " + get( name )
					+ " into a valid float, check portfolio.xml for errors" );
		}
	}

	static public long getLong(String name) {
		try {
			return Long.valueOf( get( name ) ).longValue();
		} catch (NumberFormatException e) {
			throw new RuntimeException( "Configuration could not parse " + get( name )
					+ " into a valid long, check portfolio.xml for errors" );
		}
	}

	static public short getShort(String name) {
		try {
			return Short.valueOf( get( name ) ).shortValue();
		} catch (NumberFormatException e) {
			throw new RuntimeException( "Configuration could not parse " + get( name )
					+ " into a valid short, check portfolio.xml for errors" );
		}
	}

	static public boolean getBoolean(String name) {
		return Boolean.valueOf( get( name ) ).booleanValue();
	}

	/**
	 * Initializes the Configuration class with the given URL.
	 * 
	 * @param url the url for the config file.
	 */
	static public void init(Properties properties) {
	    Configuration.properties = properties;
	}

    public static Map<String, String> getMap() {
        Map<String, String> map = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        Set<Entry<Object, Object>> entrySet = properties.entrySet();
        for (Entry<Object, Object> entry : entrySet) {
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return map;
    }
}
