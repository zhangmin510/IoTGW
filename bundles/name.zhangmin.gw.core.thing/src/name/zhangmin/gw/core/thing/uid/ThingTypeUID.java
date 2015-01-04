/**
 * 
 */
package name.zhangmin.gw.core.thing.uid;

/**
 * @author ZhangMin.name
 *
 */
public class ThingTypeUID extends UID{
	public ThingTypeUID(String uid) {
		super(uid);
	}
	
	public ThingTypeUID(String bindingID, String thingTypeId) {
		super(bindingID, thingTypeId);
	}
	
	@Override
	protected int getMinimalNumberOfSegments() {
		return 2;
	}
	
	public String getId() {
		return getSegment(1);
	}
}
