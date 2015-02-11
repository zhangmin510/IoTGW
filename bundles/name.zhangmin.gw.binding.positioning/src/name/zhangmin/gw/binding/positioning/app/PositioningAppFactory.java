/**
 * 
 */
package name.zhangmin.gw.binding.positioning.app;

import name.zhangmin.gw.core.apps.AppFactory;
import name.zhangmin.gw.core.apps.GenericApp;

/**
 * @author ZhangMin.name
 *
 */
public class PositioningAppFactory implements AppFactory{
	private final static String APP_TYPE = "Positioning";
	
	@Override
	public GenericApp createApp(String appTypeName, String appName) {
		if (appTypeName.equals(APP_TYPE)) {
			return new PositioningApp(appTypeName, appName);
		}
		return null;
	}

	@Override
	public String[] getSupportedAppTypes() {
		return new String[]{APP_TYPE};
	}

}
