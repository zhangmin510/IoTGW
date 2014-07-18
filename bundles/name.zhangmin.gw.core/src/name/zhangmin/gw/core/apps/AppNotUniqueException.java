package name.zhangmin.gw.core.apps;

import java.util.Collection;

/**
 * This exception can be thrown whenever a search pattern does not uniquely identify
 * an app. The list of matching apps must be made available through this exception.
 *
 */
public class AppNotUniqueException extends AppLookupException {

	private static final long serialVersionUID = 5154625234283910124L;

	private final Collection<App> matchingApps; 
	
	public AppNotUniqueException(String string, Collection<App> apps) {
		super("Item cannot be uniquely identified by '" + string + "'");
		this.matchingApps = apps;
	}

	/**
	 * Returns all apps that match the search pattern
	 * 
	 * @return collection of apps matching the search pattern
	 */
	public Collection<App> getMatchingApps() {
		return matchingApps;
	}

}
