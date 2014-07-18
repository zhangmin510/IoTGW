/**
 * 
 */
package name.zhangmin.gw.core.library;

import name.zhangmin.gw.core.apps.AppFactory;
import name.zhangmin.gw.core.apps.GenericApp;

/**
 * @author ZhangMin.name
 *
 */
public class CoreAppFactory implements AppFactory {

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.apps.AppFactory#createApp(java.lang.String, java.lang.String)
	 */
	@Override
	public GenericApp createApp(String appTypeName, String appName) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.apps.AppFactory#getSupportedAppTypes()
	 */
	@Override
	public String[] getSupportedAppTypes() {
		return null;
	}

}
