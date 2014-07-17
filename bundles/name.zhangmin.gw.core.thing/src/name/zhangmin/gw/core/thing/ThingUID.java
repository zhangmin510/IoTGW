/**
 * 
 */
package name.zhangmin.gw.core.thing;

/**
 * This class repesents a unique indentifier for things.
 * @author ZhangMin.name
 *
 */
public class ThingUID extends UID {

	/**
	 * Insantiates a new thing UID.
	 * @param thingTypeUID the thing type
	 * @param id the id
	 */
	public ThingUID(ThingTypeUID thingTypeUID, String id) {
		super(thingTypeUID.getBindingId(), id);
	}
	/**
	 * Instaniates a new thing UID
	 * @param bindingId the binding id
	 * @param thingTypeId the thing type id 
	 * @param id the id
	 */
	public ThingUID(String bindingId, String thingTypeId, String id) {
		super(bindingId, thingTypeId, id);
	}
	
	public ThingUID(String thingUID) {
		super(thingUID);
	}
	/**
	 * Returns the thing type id.
	 * @return thing type id
	 */
	public String getThingTypeId() {
		return getSegment(1);
	}
	
	public String getId() {
		String[] segments = getSegments();
		return segments[segments.length - 1];
	}
	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.UID#getMinimalNumberOfSegments()
	 */
	@Override
	protected int getMinimalNumberOfSegments() {
		return 3;
	}

}
