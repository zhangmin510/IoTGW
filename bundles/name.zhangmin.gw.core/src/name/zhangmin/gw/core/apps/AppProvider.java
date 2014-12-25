package name.zhangmin.gw.core.apps;

import java.util.Collection;

/**
 * An app provider provides instances of {@link GenericApp}. 
 */
public interface AppProvider {
	
	/**
	 * Provides an array of apps.
	 * 
	 * @return a collection of apps
	 */
	Collection<App> getApps();
	
	public void addAppChangeListener(AppsChangeListener listener);
	
	public void removeAppChangeListener(AppsChangeListener listener);
}
