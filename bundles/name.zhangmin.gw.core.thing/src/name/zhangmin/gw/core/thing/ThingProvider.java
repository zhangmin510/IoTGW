/**
 * 
 */
package name.zhangmin.gw.core.thing;

import java.util.Collection;

/**
 * This interface is responsible for providing things.
 * @author ZhangMin.name
 *
 */
public interface ThingProvider {
	/**
	 * Provides a collection of things.
	 * @return the things provided by the {@link ThingProvider}
	 */
	Collection<Thing> getThings();
	/**
	 * Adds a {@link ThingsChangeListener} which is notified if there are chagnes
	 * concerning the things provides by the {@link ThingProvider}.
	 * @param listener the listener to be added
	 */
	public void addThingsChangeListener(ThingsChangeListener listener);
	/**
	 * Removes a {@link ThingsChangeListener} which is notified if there are chagnes
	 * concerning the things provides by the {@link ThingProvider}.
	 * @param listener the listener to be removed
	 */
	public void removeThingsChangeListener(ThingsChangeListener listener);
	
	
}
