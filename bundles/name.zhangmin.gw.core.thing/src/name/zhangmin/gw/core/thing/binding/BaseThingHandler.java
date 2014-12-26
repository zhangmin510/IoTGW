/**
 * 
 */
package name.zhangmin.gw.core.thing.binding;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.lib.type.State;
import name.zhangmin.gw.core.thing.ChannelUID;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.ThingStatus;

/**
 * @author ZhangMin.name
 *
 */
public abstract class BaseThingHandler implements ThingHandler {
	private Thing thing;
	
	public BaseThingHandler(Thing thing) {
		this.thing = thing;
	}
	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.binding.ThingHandler#getThing()
	 */
	@Override
	public Thing getThing() {
		return thing;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.binding.ThingHandler#handleCommand(name.zhangmin.gw.core.thing.ChannelUID, name.zhangmin.gw.core.type.Command)
	 */
	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {
	
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.binding.ThingHandler#handleData(name.zhangmin.gw.core.thing.ChannelUID, name.zhangmin.gw.core.type.Data)
	 */
	@Override
	public void handleState(ChannelUID channelUID, State state) {
		
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.binding.ThingHandler#dispose()
	 */
	@Override
	public void dispose() {

	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.thing.binding.ThingHandler#initialize()
	 */
	@Override
	public void initialize() {

	}
	
	protected Configuration getConfig() {
		return getThing().getConfiguration();
	}
    /**
     * Returns the configuration of the thing and transforms it to the given
     * class.
     * 
     * @param configurationClass
     *            configuration class
     * @return configuration of thing in form of the given class
     */
    protected <T> T getConfigAs(Class<T> configurationClass) {
        return getConfig().as(configurationClass);
    }
    /**
     * 
     * Updates the state of the thing.
     * 
     * @param channelUID
     *            unique id of the channel, which was updated
     * @param state
     *            new state
     */
    protected void updateState(ChannelUID channelUID, State state) {
        thing.channelUpdated(channelUID, state);
    }

    /**
     * Updates the status of the thing.
     * 
     * @param status
     *            new status
     */
    protected void updateStatus(ThingStatus status) {
        if (thing.getStatus() != status) {
            thing.setStatus(status);
        }
    }    

}
