/**
 * 
 */
package name.zhangmin.gw.core.thing;

/**
 * This class represents a unique identifier for channels.
 * 
 * @author ZhangMin.name
 *
 */
public class ChannelUID extends UID {
	public ChannelUID(String channelId) {
		super(channelId);
	}
	
	/**
	 * Instantiate a ChannelUID
	 * @param thingUID the unique identifier of the thing the channel belongs to
	 * @param id the channel's id
	 */
	public ChannelUID(ThingUID thingUID, String id) {
		super(thingUID.getBindingId(), thingUID.getThingTypeId(), thingUID.getId(), id);
	}
	
	/**
	 * 
	 * @param thingTypeUID the unique id of the thing's thingType
	 * @param thingId the id of the thing the channel belongs to
	 * @param id the channel id
	 */
	public ChannelUID(ThingTypeUID thingTypeUID, String thingId, String id) {
		super(thingTypeUID.getBindingId(), thingTypeUID.getId(), thingId, id);
	}
	
	public ChannelUID(String bindingId, String thingTypeId, String thingId, String id) {
		super(bindingId, thingTypeId, thingId, id);
	}
	
	public String getThingTypeId() {
		return getSegment(1);
	}
	public String getThingId() {
		return getSegment(2);
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
		return 4;
	}

}
