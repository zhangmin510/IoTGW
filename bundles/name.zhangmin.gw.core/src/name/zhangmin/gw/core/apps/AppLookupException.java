
package name.zhangmin.gw.core.apps;

/**
 * This is an abstract parent exception to be extended by any exceptions
 * related to app lookups in the app registry.
 * 
 * @author ZhangMin.name - Initial contribution
 *
 */
public abstract class AppLookupException extends Exception {
	
	public AppLookupException(String string) {
		super(string);
	}

	private static final long serialVersionUID = -4617708589675048859L;

}
