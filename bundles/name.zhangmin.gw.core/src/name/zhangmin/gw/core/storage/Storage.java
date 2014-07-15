/**
 * 
 */
package name.zhangmin.gw.core.storage;

import java.util.Collection;

/**
 * This interface is the generic way to store key-value pairs. Each
 * Storage implementation can store its data differently, e.g in-memeroy
 * or in-database.
 * 
 * @author ZhangMin.name
 *
 */
public interface Storage<T> {
	/**
	 * Puts a key-value mapping into this storage.
	 * 
	 * @param key the key to add
	 * @param value the value to add
	 * @return previous value for the key or null if no value was replaced
	 */
	T put(String key, T value);
	/**
	 * Removes the specifiec mapping from this map.
	 * 
	 * @param key the mapping key to remove
	 * @return the removed value or null if no entry existed
	 */
	T remove(String key);
	/**
	 * Gets the value mapped to the key specified.
	 * 
	 * @param key the key
	 * @return the mapped value, null if no match
	 */
	T get(String key);
	/**
	 * Gets all keys of this storage.
	 * 
	 * @return the keys of this storage
	 */
	Collection<String> getKeys();
	/**
	 * Get all values of this storage.
	 * 
	 * @return the values of this storage
	 */
	Collection<T> getValues();
}
