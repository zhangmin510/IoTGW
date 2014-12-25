/**
 * 
 */
package name.zhangmin.gw.core.storage.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import name.zhangmin.gw.core.storage.Storage;

/**
 * This class is an implementation which stores it's data in-memory.
 * 
 * @author ZhangMin.name
 *
 */
public class VolatileStorage<T> implements Storage<T> {
	Map<String, T> storage = new ConcurrentHashMap<String, T>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T put(String key, T value) {
		return storage.put(key, value);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T remove(String key) {
		return storage.remove(key);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(String key) {
		return storage.get(key);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getKeys() {
		return storage.keySet();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<T> getValues() {
		return storage.values();
	}

}
