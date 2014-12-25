package name.zhangmin.gw.core.apps;


/**
 * This Factory creates concrete instances of the known appTypes.

 */
public interface AppFactory {
	
	/**
	 * Creates a new app instance of type <code>appTypeName</code> and the name
	 * <code>appName</code> 
	 * 
	 * @param appTypeName
	 * @param appName
	 * 
	 * @return a new app of type <code>appTypeName</code> or
	 * <code>null</code> if no matching class is known.
	 */
	GenericApp createApp(String appTypeName, String appName);
	
	/**
	 * Returns the list of all supported appTypes of this Factory.
	 * 
	 * @return the supported itemTypes
	 */
	String[] getSupportedAppTypes();
}
