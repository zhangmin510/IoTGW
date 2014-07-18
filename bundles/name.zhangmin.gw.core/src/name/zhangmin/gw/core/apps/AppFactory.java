package name.zhangmin.gw.core.apps;


/**
 * This Factory creates concrete instances of the known itemTypes.

 */
public interface AppFactory {
	
	/**
	 * Creates a new item instance of type <code>appTypeName</code> and the name
	 * <code>appName</code> 
	 * 
	 * @param appTypeName
	 * @param appName
	 * 
	 * @return a new item of type <code>appTypeName</code> or
	 * <code>null</code> if no matching class is known.
	 */
	GenericApp createApp(String appTypeName, String appName);
	
	/**
	 * Returns the list of all supported itemTypes of this Factory.
	 * 
	 * @return the supported itemTypes
	 */
	String[] getSupportedAppTypes();
}
