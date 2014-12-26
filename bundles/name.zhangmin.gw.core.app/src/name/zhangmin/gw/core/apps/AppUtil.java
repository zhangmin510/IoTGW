/**
 * 
 */
package name.zhangmin.gw.core.apps;

/**
 * This class contains utility method for {@link App} objects
 * <p>
 * This class cannot be instantiated, it only contains static methods.
 * 
 * @author ZhangMin.name
 *
 */
public class AppUtil {
	
	private AppUtil() { }
	
	/**
	 * Returns {@code true} if the specified name is valid app name,
	 * otherwise {@code false}.
	 * <p>
	 * A valid app name must <i>only</i> consist of the following characters:
	 * <ul>
	 * <li>a-z</li>
	 * <li>A-Z</li>
	 * <li>0-9</li>
	 * <li>_(underscore)</li>
	 * </ul>
	 * 
	 * @param appName the name to be checked (could be null or empty)
	 * @return true if sepecified name is valid app name, otherwise false.
	 */
	public static boolean isValidAppName(String appName) {
		if ((appName != null) && (!appName.isEmpty()))
			return appName.matches("[a-zA-Z0-9_]*");
		return false;
	}
	public static void assertValidAppName(String appName) throws IllegalArgumentException {
		if (!isValidAppName(appName)) {
			throw new IllegalArgumentException("The specified name of the app " +
					appName + " is not valid!");
		}
	}
}
