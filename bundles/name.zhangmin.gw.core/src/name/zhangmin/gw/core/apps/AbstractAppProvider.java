/**
 * 
 */
package name.zhangmin.gw.core.apps;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is base class for {@link AppProvider}s. In particular it 
 * handles the management and notification of {@link AppsChangeListener}.
 * 
 * @author ZhangMin.name
 *
 */
public abstract class AbstractAppProvider implements AppProvider {

	protected Collection<AppsChangeListener> appsChangeListeners = 
			new CopyOnWriteArrayList<AppsChangeListener>();


	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.apps.AppProvider#addAppChangeListener(name.zhangmin.gw.core.apps.AppsChangeListener)
	 */
	@Override
	public void addAppChangeListener(AppsChangeListener listener) {
		appsChangeListeners.add(listener);

	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.apps.AppProvider#removeAppChangeListener(name.zhangmin.gw.core.apps.AppsChangeListener)
	 */
	@Override
	public void removeAppChangeListener(AppsChangeListener listener) {
		appsChangeListeners.remove(listener);

	}
	
    protected void notifyAppChangeListenersAboutAddedApp(App app) {
        for (AppsChangeListener appsChangeListener : appsChangeListeners) {
            appsChangeListener.appAdded(this, app);
        }
    }

    protected void notifyAppChangeListenersAboutRemovedApp(App app) {
        for (AppsChangeListener appsChangeListener : appsChangeListeners) {
            appsChangeListener.appRemoved(this, app);
        }
    }

    protected void notifyAppChangeListenersAboutAllAppsChanged(Collection<String> oldAppNames) {
        for (AppsChangeListener appsChangeListener : appsChangeListeners) {
            appsChangeListener.allAppsChanged(this, oldAppNames);
        }
    }
}
