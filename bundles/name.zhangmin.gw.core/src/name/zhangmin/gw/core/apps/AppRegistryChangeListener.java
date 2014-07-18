package name.zhangmin.gw.core.apps;

import java.util.Collection;

/**
 * This is a listener interface which should be implemented where ever the app registry is
 * used in order to be notified of any dynamic changes in the provided apps.
 * 
 *
 */
public interface AppRegistryChangeListener {

	/**
	 * Notifies the listener that all apps in the registry have changed and thus should be reloaded.
	 * 
	 * @param oldAppNames a collection of all previous app names, so that references can be removed
	 */
	public void allAppsChanged(Collection<String> oldAppNames);

	/**
	 * Notifies the listener that a single app has been added
	 * 
	 * @param app the app that has been added
	 */
	public void appAdded(App app);
	
	/**
	 * Notifies the listener that a single app has been removed
	 * 
	 * @param app the app that has been removed
	 */
	public void appRemoved(App app);
	
}
