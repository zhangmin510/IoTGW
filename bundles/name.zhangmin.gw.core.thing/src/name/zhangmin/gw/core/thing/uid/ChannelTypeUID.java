/**
 * 
 */
package name.zhangmin.gw.core.thing.uid;


/**
 * This class represents a unique indentifier for channel types.
 * 
 * @author ZhangMin.name
 *
 */
public class ChannelTypeUID extends UID{
    public ChannelTypeUID(String channelUid) {
        super(channelUid);
    }

    public ChannelTypeUID(String bindingId, String id) {
        super(bindingId, id);
    }

    @Override
    protected int getMinimalNumberOfSegments() {
        return 2;
    }

    public String getId() {
        return getSegment(1);
    }
}
