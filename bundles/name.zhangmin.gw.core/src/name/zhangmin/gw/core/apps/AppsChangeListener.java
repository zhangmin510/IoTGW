package name.zhangmin.gw.core.apps;

import java.util.Collection;

/**
 * This is a listener interface which should be implemented where ever app providers or
 * the app registry are used in order to be notified of any dynamic changes in the provided apps.
 * 
 *
 */
public interface AppsChangeListener {

	/**
	 * Notifies the listener that all apps of a provider have changed and thus should be reloaded.
	 * 
	 * @param provider the concerned app provider 
	 * @param oldAppNames a collection of all previous app names, so that references can be removed
	 */
	public void allAppsChanged(AppProvider provider, Collection<String> oldAppNames);

	/**
	 * Notifies the listener that a single app has been added
	 * 
	 * @param provider the concerned app provider 
	 * @param app the app that has been added
	 */
	public void appAdded(AppProvider provider, App app);
	
	/**
	 * Notifies the listener that a single app has been removed
	 * 
	 * @param provider the concerned app provider 
	 * @param app the app that has been removed
	 */
	public void appRemoved(AppProvider provider, App app);
	
}
