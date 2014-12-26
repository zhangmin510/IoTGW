/**
 * 
 */
package name.zhangmin.gw.core.apps.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import name.zhangmin.gw.core.apps.App;
import name.zhangmin.gw.core.apps.AppNotFoundException;
import name.zhangmin.gw.core.apps.AppNotUniqueException;
import name.zhangmin.gw.core.apps.AppProvider;
import name.zhangmin.gw.core.apps.AppRegistry;
import name.zhangmin.gw.core.apps.AppRegistryChangeListener;
import name.zhangmin.gw.core.apps.AppsChangeListener;
import name.zhangmin.gw.core.apps.GenericApp;
import name.zhangmin.gw.core.event.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is main implementing class of the {@link AppRegistry} interface.
 * It keeps track of all declared app of all app providers and keeps their
 * curren state in memeory. This is central point where states are kept
 * and thus it is a core part of all stateful services.
 * 
 * @author ZhangMin.name
 *
 */
public class AppRegistryImpl implements AppRegistry, AppsChangeListener{
	private static final Logger logger = LoggerFactory.getLogger(AppRegistryImpl.class);

	/** if an EventPublisher service is available, we provide it to all apps, so that they can communicate over the bus */
	protected EventPublisher eventPublisher;
	
	/** this is our local map in which we store all our apps */
	protected Map<AppProvider, Collection<App>> appMap = new ConcurrentHashMap<AppProvider, Collection<App>>();
	
	/** to keep track of all app change listeners */
	protected Collection<AppRegistryChangeListener> listeners = new CopyOnWriteArraySet<AppRegistryChangeListener>();

	public void activate() {
	}
	
	public void deactivate() {
		// first remove ourself as a listener from the app providers
		for(AppProvider provider : appMap.keySet()) {
			provider.removeAppChangeListener(this);
		}
		// then release all apps
		appMap.clear();
    }

	/* (non-Javadoc)
	 * @see org.eclipse.smarthome.core.internal.apps.AppRegistry#getApp(java.lang.String)
	 */
    @Override
	public App getApp(String name) throws AppNotFoundException {
		for(Collection<App> apps : appMap.values()) {
			for(App app : apps) {
				if(app.getName().matches(name)) {
					return app;
				}
			}
		}
		throw new AppNotFoundException(name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.smarthome.core.internal.apps.AppRegistry#getAppByPattern(java.lang.String)
	 */
    @Override
	public App getAppByPattern(String name) throws AppNotFoundException, AppNotUniqueException {
		Collection<App> apps = getApps(name);
		
		if(apps.isEmpty()) {
			throw new AppNotFoundException(name);
		}
		
		if(apps.size()>1) {
			throw new AppNotUniqueException(name, apps);
		}
		
		return apps.iterator().next();
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.smarthome.core.internal.apps.AppRegistry#getApps()
	 */
    @Override
	public Collection<App> getApps() {
		Collection<App> allApps = new ArrayList<App>();
		for(Collection<App> apps : appMap.values()) {
			allApps.addAll(apps);
		}
		return allApps;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.smarthome.core.internal.apps.AppRegistry#getApps(java.lang.String)
	 */
    @Override
	public Collection<App> getApps(String pattern) {
		String regex = pattern.replace("?", ".?").replace("*", ".*?");
		Collection<App> matchedApps = new ArrayList<App>();
		for(Collection<App> apps : appMap.values()) {
			for(App app : apps) {
				if(app.getName().matches(regex)) {
					matchedApps.add(app);
				}
			}
		}
        return matchedApps;
	}

	public void addAppProvider(AppProvider appProvider) {
		// only add this provider if it does not already exist
		if(!appMap.containsKey(appProvider)) {
			Collection<App> apps = new CopyOnWriteArraySet<App>(appProvider.getApps());
			appProvider.addAppChangeListener(this);
        	appMap.put(appProvider, apps);
			logger.debug("App provider '{}' has been added.", appProvider.getClass().getSimpleName());
			allAppsChanged(appProvider, null);
		}
	}

    @Override
	public boolean isValidAppName(String name) {
		return name.matches("[a-zA-Z0-9_]*");
	}

	public void removeAppProvider(AppProvider appProvider) {
		if(appMap.containsKey(appProvider)) {
			allAppsChanged(appProvider, null);

			for(App app : appMap.get(appProvider)) {
				if(app instanceof GenericApp) {
					((GenericApp) app).dispose();
				}
			}
			appMap.remove(appProvider);

			appProvider.removeAppChangeListener(this);
			logger.debug("App provider '{}' has been removed.", appProvider.getClass().getSimpleName());
		}
	}
	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
        for(App app : getApps()) {
            ((GenericApp)app).setEventPublisher(eventPublisher);
        }
	}
	
	public void unsetEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = null;
        for(App app : getApps()) {
            ((GenericApp)app).setEventPublisher(null);
        }
	}

    @Override
	public void allAppsChanged(AppProvider provider, Collection<String> oldAppNames) {
		// if the provider did not provide any old app names, we check if we
		// know them and pass them further on to our listeners
		if(oldAppNames==null || oldAppNames.isEmpty()) {
			oldAppNames = new HashSet<String>();
            Collection<App> oldApps;
            oldApps = appMap.get(provider);
			if(oldApps!=null && oldApps.size() > 0) {
				for(App oldApp : oldApps) {
					oldAppNames.add(oldApp.getName());
				}
			}
		}

		Collection<App> apps = new CopyOnWriteArrayList<App>();
    	appMap.put(provider, apps);
		for(App app : provider.getApps()) {
			if(initializeApp(app)) {
				apps.add(app);
			}
		}

		for(AppRegistryChangeListener listener : listeners) {
			listener.allAppsChanged(oldAppNames);
		}
	}

    @Override
	public void appAdded(AppProvider provider, App app) {
        Collection<App> apps;
        apps = appMap.get(provider);
		if(apps!=null) {
			if(initializeApp(app)) {
				apps.add(app);
			} else {
				return;
			}
		}
		for(AppRegistryChangeListener listener : listeners) {
			listener.appAdded(app);
		}
	}

    @Override
	public void appRemoved(AppProvider provider, App app) {
        Collection<App> apps;
        apps = appMap.get(provider);
		if(apps!=null) {
			apps.remove(app);
		}
		for(AppRegistryChangeListener listener : listeners) {
			listener.appRemoved(app);
		}
	}

    @Override
	public void addAppRegistryChangeListener(AppRegistryChangeListener listener) {
		listeners.add(listener);
	}

    @Override
	public void removeAppRegistryChangeListener(AppRegistryChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * an app should be initialized, which means that the event publisher is
	 * injected and its implementation is notified that it has just been created,
	 * so it can perform any task it needs to do after its creation.
	 * 
	 * @param app the app to initialize
	 * @return false, if the app has no valid name
	 */
	private boolean initializeApp(App app) {
		if(isValidAppName(app.getName())) {
			if(app instanceof GenericApp) {
				GenericApp genericApp = (GenericApp) app;
				genericApp.setEventPublisher(eventPublisher);
				genericApp.initialize();
			}
			
			return true;
		} else {
			logger.warn("Ignoring app '{}' as it does not comply with" +
					" the naming convention.", app.getName());
			return false;
		}
	}
}
