/**
 * 
 */
package name.zhangmin.gw.core.thing.binding;

import java.util.Collection;

import name.zhangmin.gw.core.thing.type.ThingType;

/**
 * This class is responsible for providing thing type.
 * 
 * @author ZhangMin.name
 *
 */
public interface ThingTypeProvider {

	/**
	 * Provides a collection of thing types
	 * @return the thing types provided bye the {@link ThingTypeProvider}
	 */
	Collection<ThingType> getThingTypes();
	
	/**
	 * Adds a {@link ThingTypeChangeListener} which is notified if there
	 * are changes concerning the thing type provided by the {@link ThingTypeProvider}
	 * 
	 * @param listener the listener to be added
	 */
	public void addThingTypeChangeListener(ThingTypeChangeListener listener);
	
	/**
	 * Removes a {@link ThingTypeChangeListener} which is notified if there
	 * are changes concerning the thing types provided by the {@link ThingTypeProvider}
	 * 
	 * @param listener
	 */
	public void removeThingTypeChangeListener(ThingTypeChangeListener listener);
}
