/**
 * 
 */
package name.zhangmin.gw.core.thing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.thing.binding.ThingHandler;
import name.zhangmin.gw.core.thing.uid.ChannelUID;
import name.zhangmin.gw.core.thing.uid.ThingTypeUID;
import name.zhangmin.gw.core.thing.uid.ThingUID;
import name.zhangmin.gw.core.lib.type.State;

/**
 * @author ZhangMin.name
 *
 */
public class ThingImpl implements Thing {
	private List<Channel> channels;
	private Configuration configuration;
	private ThingUID uid;
	volatile private ThingStatus status;
	volatile private ThingHandler thingHandler;
	volatile private List<ThingListener> thingListeners = 
			new CopyOnWriteArrayList<>();
	private String name;
	private ThingTypeUID thingTypeUID;
	
	
	public ThingImpl(ThingTypeUID thingTypeUID, String thingId) 
		throws IllegalArgumentException {
		this.uid = new ThingUID(thingTypeUID.getBindingId(), thingTypeUID.getId(), thingId);
		this.thingTypeUID = thingTypeUID;
		this.channels = new ArrayList<>(0);
	}
    
    /**
     * @param thingUID
     * @throws IllegalArgumentException
     */
    public ThingImpl(ThingUID thingUID) throws IllegalArgumentException {
    	this.uid = thingUID;
    	this.thingTypeUID = new ThingTypeUID(thingUID.getBindingId(), thingUID.getThingTypeId());
    	this.channels = new ArrayList<>(0);
    }

    /**
     * Adds the thing listener.
     * 
     * @param thingListener
     *            the thing listener
     */
    public void addThingListener(ThingListener thingListener) {
        this.thingListeners.add(thingListener);
    }
    /**
     * Removes the thing listener.
     * 
     * @param thingListener
     *            the thing listener
     */
    public void removeThingListener(ThingListener thingListener) {
        this.thingListeners.remove(thingListener);
    }
	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#getChannels()
	 */
	@Override
	public List<Channel> getChannels() {
		return this.channels;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#getStatus()
	 */
	@Override
	public ThingStatus getStatus() {
		return this.status;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#setStatus(name.zhangmin.gw.core.thing.ThingStatus)
	 */
	@Override
	public void setStatus(ThingStatus status) {
		this.status = status;

	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#setHandler(name.zhangmin.gw.core.thing.binding.ThingHandler)
	 */
	@Override
	public void setHandler(ThingHandler thingHandler) {
		this.thingHandler = thingHandler;

	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#getHandler()
	 */
	@Override
	public ThingHandler getHandler() {
		return this.thingHandler;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#channelUpdated(name.zhangmin.gw.core.thing.ChannelUID, name.zhangmin.gw.core.type.State)
	 */
	@Override
	public void channelUpdated(ChannelUID channelUID, State data) {
		for (ThingListener thingListener : thingListeners) 
			thingListener.channelUpdated(channelUID, data);
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#getConfiguration()
	 */
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;

	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#getUID()
	 */
	@Override
	public ThingUID getUID() {
		return this.uid;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.Thing#getThingTypeUID()
	 */
	@Override
	public ThingTypeUID getThingTypeUID() {
		return this.thingTypeUID;
	}
    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public void setConfiguration(Configuration configuation) {
        this.configuration = configuation;
    }

    public void setId(ThingUID id) {
        this.uid = id;
    }	 
}
