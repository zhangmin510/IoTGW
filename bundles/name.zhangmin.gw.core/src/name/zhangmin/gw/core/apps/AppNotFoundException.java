
package name.zhangmin.gw.core.apps;

/**
 * This exception is thrown by the {@link AppRegistry} if an item could
 * not be found.
 * 
 */
public class AppNotFoundException extends AppLookupException {

	public AppNotFoundException(String name) {
		super("Item '" + name + "' could not be found in the item registry");
	}

	private static final long serialVersionUID = -3720784568250902711L;

}
