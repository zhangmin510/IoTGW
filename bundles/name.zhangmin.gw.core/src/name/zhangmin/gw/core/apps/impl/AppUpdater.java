/**
 * 
 */
package name.zhangmin.gw.core.apps.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.core.apps.AppNotFoundException;
import name.zhangmin.gw.core.apps.AppRegistry;
import name.zhangmin.gw.core.apps.GenericApp;
import name.zhangmin.gw.core.event.impl.AbstractEventSubscriber;
import name.zhangmin.gw.core.type.Command;
import name.zhangmin.gw.core.type.Data;
import name.zhangmin.gw.core.type.State;

/**
 * The AppUpdater listens on the event bus and passes any received status
 * update to the app registry.
 * @author ZhangMin.name
 *
 */
public class AppUpdater extends AbstractEventSubscriber {
	public AppUpdater() {
		super();
		getSourceFilterList().clear();
	}
	private static final Logger logger = LoggerFactory.getLogger(AppUpdater.class);
	protected AppRegistry appRegistry;
	
	public void setAppRegistry(AppRegistry appRegistry) {
		this.appRegistry = appRegistry;
	}
	public void unsetAppRegistry(AppRegistry appRegistry) {
		this.appRegistry = null;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receiveData(String appName, Data newData) {
		if (appRegistry != null) {
			try {
				GenericApp app = (GenericApp) appRegistry.getApp(appName);
				boolean isAccepted = false;
				if (app.getAcceptedDataTypes().contains(newData.getClass())) isAccepted = true;
				else {
					for (Class<? extends Data> data : app.getAcceptedDataTypes()) {
						try {
							if (!data.isEnum() && data.newInstance().getClass().isAssignableFrom(newData.getClass())) {
								isAccepted = true;
								break;
							}
						} catch (InstantiationException e) {
							logger.warn("InstantiationException on {} ", e.getMessage()); // should never happen
						} catch (IllegalAccessException e) {
							logger.warn("IllegalAccessException on {}", e.getMessage()); // should never happen
						}
					}
				}
				//TODO IoTGW type not compile
				if (isAccepted) app.setState((State)newData);
				else logger.debug("Received update of a not acceted type (" + newData.getClass().getSimpleName() + ") for app" + appName);
			} catch (AppNotFoundException e) {
				logger.debug("Received update for non-existing app:{}", e.getMessage());
			}
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void receiveCommand(String appName, Command command) {
	
	}
}
