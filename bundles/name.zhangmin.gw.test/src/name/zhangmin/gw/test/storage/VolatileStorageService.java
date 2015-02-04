package name.zhangmin.gw.test.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import name.zhangmin.gw.core.storage.Storage;
import name.zhangmin.gw.core.storage.StorageService;



/**
 * The {@link VolatileStorageService} returns {@link VolatileStorage}s
 * which stores their data in-memory.
 * 
 * @author ZhangMin.name
 */
public class VolatileStorageService implements StorageService {
	
	@SuppressWarnings("rawtypes")
	Map<String, Storage> storages = new ConcurrentHashMap<String, Storage>();
	
	
	/**
	 * {@inheritDoc}
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T> Storage<T> getStorage(String name) {
		if (!storages.containsKey(name)) {
			storages.put(name, new VolatileStorage<T>());
		}
		return storages.get(name);
	}

    /**
     * {@inheritDoc}
     * 
     * @return
     */
    public <T> Storage<T> getStorage(String name, ClassLoader classLoader) {
        return getStorage(name);
    }
	
}
