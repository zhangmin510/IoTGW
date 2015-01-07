/**
 * 
 */
package name.zhangmin.gw.core.storage;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * This class helps to select a storage based on the StorageService
 * with the highest service ranking.
 * 
 * @author ZhangMin.name
 * 
 * @param <T> Type of the storage
 *
 */
public class StorageSelector<T> {
	private BundleContext bundleContext;
	
	private Collection<StorageService> storageServiceCandidates = 
			new CopyOnWriteArrayList<StorageService>();
	
	private StorageSelectionListener<T> storageSelectionListener;
	
	private String storageName;
	
	public StorageSelector(BundleContext bundleContext, String storageName,
			StorageSelectionListener<T> storageSelectionListener) {
		this.bundleContext = bundleContext;
		this.storageName = storageName;
		this.storageSelectionListener = storageSelectionListener;
	}
	/**
	 * This interface must be implemented to get notified about a slected storage.
	 * @author ZhangMin.name
	 *
	 * @param <T> Type of the storage
	 */
	public interface StorageSelectionListener<T> {
		void storageSelected(Storage<T> storage);
	}
	@SuppressWarnings ("unchecked")
	public void addStorageService(StorageService storageService) {
		this.storageServiceCandidates.add(storageService);
		
		// samll optimization - if there is only one service available
		// we don't have to select amongst others
		if (this.storageServiceCandidates.size() == 1) {
			this.storageSelectionListener.storageSelected(
					(Storage<T>)storageService.getStorage(this.storageName));
		}
	}
	/**
	 * Returns a {@link Storage} returned by a {@link StorageService} with
	 * the highest priority availabe in the OSGi container. In theory this should
     * not be necessary if DS would have taken the {@code service.ranking}
     * property into account properly. Unfortunately it haven't during my tests.
     * So this method should be seen as workaround until somebody proofs that DS
     * evaluates the property correctly.
     * 
	 * @return a {@link Storage} created by the {@link StorageService} with the highest
	 * 	priority(according to the OSGi container)
	 */
	private Storage<T> findStorageServiceByPriority() {
		ServiceReference<?> reference = this.bundleContext.getServiceReference(StorageService.class);
		
		if (reference != null) {
			StorageService service = (StorageService) StorageServiceActivator.getContext().getService(reference);
			Storage<T> storage = service.getStorage(this.storageName);
			return storage;
		}
		
		throw new IllegalStateException(
				"There is no Service of Type 'StorageService' available. This should not happen!");
		
	}
	
	public void removeStorageService(StorageService storageService) {
		this.storageServiceCandidates.remove(storageService);
		
		// if there are still StorageService left, we have to select
		// a new one to take over
		if (this.storageServiceCandidates.size() > 0) {
			this.storageSelectionListener.storageSelected(this.findStorageServiceByPriority());;
		} else {
			this.storageSelectionListener.storageSelected(null);
		}
	}
}
