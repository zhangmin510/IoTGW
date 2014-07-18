package name.zhangmin.gw.core.apps;

import java.util.Collection;

/**
 * The AppRegistry is the central place, where apps are kept in memory and their state
 * is permanently tracked. So any code that requires the current state of apps should use
 * this service (instead of trying to keep their own local copy of the apps).
 * 
 * Apps are registered by {@link AppProvider}s, which can provision them from any source
 * they like and also dynamically remove or add apps.
 * 
 *
 */
public interface AppRegistry {

	/**
	 * This method retrieves a single app from the registry.
	 * 
	 * @param name the app name
	 * @return the uniquely identified app
	 * @throws AppNotFoundException if no app matches the input
	 */
	public App getApp(String name) throws AppNotFoundException;

	/**
	 * This method retrieves a single app from the registry.
	 * Search patterns and shortened versions are supported, if they uniquely identify an app
	 * 
	 * @param name the app name, a part of the app name or a search pattern
	 * @return the uniquely identified app
	 * @throws AppNotFoundException if no app matches the input
	 * @throws AppNotUniqueException if multiply apps match the input
	 */
	public App getAppByPattern(String name) throws AppNotFoundException, AppNotUniqueException;

	/**
	 * This method retrieves all apps that are currently available in the registry
	 * 
	 * @return a collection of all available apps
	 */
	public Collection<App> getApps();

	/**
	 * This method retrieves all apps that match a given search pattern
	 * 
	 * @return a collection of all apps matching the search pattern
	 */
	public Collection<App> getApps(String pattern);

	/**
	 * Checks whether appName matches the app name conventions.
	 * App names must only consist out of alpha-numerical characters and
	 * underscores (_).
	 * 
	 * @param appName the app name to validate 
	 * @return true, if the name is valid
	 */
	public boolean isValidAppName(String appName);

	public void addAppRegistryChangeListener(AppRegistryChangeListener listener);
	
	public void removeAppRegistryChangeListener(AppRegistryChangeListener listener);

}
