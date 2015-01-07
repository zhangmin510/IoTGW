/**
 * 
 */
package name.zhangmin.gw.core.apps.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.core.apps.AppActivator;
import name.zhangmin.gw.core.apps.AbstractAppProvider;
import name.zhangmin.gw.core.apps.App;
import name.zhangmin.gw.core.apps.AppFactory;
import name.zhangmin.gw.core.apps.AppProvider;
import name.zhangmin.gw.core.apps.AppRegistry;
import name.zhangmin.gw.core.apps.GenericApp;
import name.zhangmin.gw.core.storage.Storage;
import name.zhangmin.gw.core.storage.StorageSelector;
import name.zhangmin.gw.core.storage.StorageSelector.StorageSelectionListener;
import name.zhangmin.gw.core.storage.StorageService;

/**
 * This class is an OSGi service, that allows to add or remove
 * apps at runtime by call {@link ManagedAppProvider#addApp(App)} or
 * {@link ManagedAppProvider#removeApp(App)}. An added app is automatically
 * exposed to the {@link AppRegistry}. Persistence of added apps is handled
 * by a {@link StorageService}. Apps are being restored using the given
 * {@link AppFactory}s.
 * 
 * @author ZhangMin.name
 *
 */
public class ManagedAppProvider extends AbstractAppProvider implements StorageSelectionListener<String>{
	private static final Logger logger = 
			LoggerFactory.getLogger(ManagedAppProvider.class);

		private Storage<String> appStorage;
		private Collection<AppFactory> appFactories = new CopyOnWriteArrayList<AppFactory>();

	    private StorageSelector<String> storageSelector;
		
	    public ManagedAppProvider() {
	        storageSelector = new StorageSelector<>(AppActivator.getContext(),
	                App.class.getName(), this);
	    }

		public void setStorageService(StorageService storageService) {
			System.out.println("set storage service");
	        storageSelector.addStorageService(storageService);
		}

		public void removeStorageService(StorageService storageService) {
	        storageSelector.removeStorageService(storageService);
		}

		public void addAppFactory(AppFactory appFactory) {
			appFactories.add(appFactory);
			System.out.println("add appfactory");
		}

		public void removeAppFactory(AppFactory appFactory) {
			appFactories.remove(appFactory);
		}
		
		
		public App addApp(App app) {
			if (app == null) {
				throw new IllegalArgumentException("Cannot add null App.");
			}

			String oldAppType = appStorage.put(app.getName(), toAppFactoryName(app));
			App oldApp = null;
			if(oldAppType!=null) {
				oldApp = instantiateApp(oldAppType, app.getName());
				notifyAppChangeListenersAboutRemovedApp(oldApp);
			}
			notifyAppChangeListenersAboutAddedApp(app);
			return oldApp;
		}

		/**
		 * Translates the Apps class simple name into a type name understandable by
		 * the {@link AppFactory}s.
		 * 
		 * @param app  the App to translate the name
		 * @return the translated AppTypeName understandable by the
		 *         {@link AppFactory}s
		 */
		private String toAppFactoryName(App app) {
			return app.getType();
		}

		public App removeApp(String appName) {
			if (appName == null) {
				throw new IllegalArgumentException("Cannot remove null App");
			}

			String removedAppType = appStorage.remove(appName);
			if (removedAppType!=null) {
				App removedApp = instantiateApp(removedAppType, appName);
				notifyAppChangeListenersAboutRemovedApp(removedApp);
				return removedApp;
			}
			return null;
		}

		/**
		 * Returns all Apps of this {@link AppProvider} being restored from the
		 * underlying {@link StorageService} and instantiated using the appropriate
		 * {@link AppFactory}s.
		 * 
		 * {@inheritDoc}
		 */
		@Override
		public Collection<App> getApps() {
			Collection<App> storedApps = new ArrayList<App>();
			for (String appName : appStorage.getKeys()) {
				String appTypeName = appStorage.get(appName);
				storedApps.add(instantiateApp(appTypeName, appName));
			}
			return storedApps;
		}

		private App instantiateApp(String appTypeName, String appName) {
			for (AppFactory appFactory : appFactories) {
				GenericApp app = appFactory.createApp(appTypeName, appName);
				if (app != null) {
					return app;
				}
			}
			logger.debug(
					"Couldn't restore app '{}' of type '{}' there is no appropriate AppFactory available.",
					appName, appTypeName);
				return null;
		}

	    @Override
	    public void storageSelected(Storage<String> storage) {
	        this.appStorage = storage;
	    }
}
