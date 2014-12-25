/**
 * 
 */
package name.zhangmin.gw.core.storage.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import name.zhangmin.gw.core.storage.Storage;
import name.zhangmin.gw.core.storage.StorageService;

/**
 * @author ZhangMin.name
 *
 */
public class VolatileStorageService implements StorageService{

	@SuppressWarnings("rawtypes")
	Map<String, Storage> storages = new ConcurrentHashMap<String, Storage>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized <T> Storage<T> getStorage(String name) {
		if (!storages.containsKey(name)) {
			storages.put(name, new VolatileStorage<T>());
		}
		return storages.get(name);
	}

}
