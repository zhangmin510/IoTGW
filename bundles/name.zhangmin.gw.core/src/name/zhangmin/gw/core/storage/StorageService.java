/**
 * 
 */
package name.zhangmin.gw.core.storage;

/**
 * This interface provides instance of {@link Storage}s
 * which are meant as a means for generic storage of key-value pairs.
 * You can think of different {@link StorageService}s that store these
 * key-value pairs differently.On can think of e.g in-memeory or
 * in-database {@link Storage}s and many more.This {@link StorageService}
 * decides which kind of {@link Storage} is returned on request. It is 
 * meant to be injected into service consumers with the need for storing
 * generic key-value pairs like the ManagedXXXProviders.
 * 
 * @author ZhangMin.name
 *
 */
public interface StorageService {
	/**
	 * Returns the {@link Storage} with the given {@code name}. If no
	 * {@link Storage} with this name exists a new initialized intance
	 * is returned.
	 * 
	 * @param name the name of the {@link StorageService} to return
	 * @return a ready to use {@link Storage}, never {@code null}
	 */
	<T> Storage<T> getStorage(String name);
}
