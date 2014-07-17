/**
 * 
 */
package name.zhangmin.gw.core.thing.impl;

import java.util.EventListener;

import name.zhangmin.gw.core.thing.ChannelUID;
import name.zhangmin.gw.core.type.Data;

/**
 * {@link ThingListener} can be registered at a {@link Thing} object.
 * 
 * @see Thing#addThingListener(ThingListener)
 * @author ZhangMin.name
 */
public interface ThingListener extends EventListener {

    /**
     * Channel updated is called when the state of a channel was updated.
     * 
     * @param channelUID
     *            unique identifier of a channel
     * @param state
     *            state
     */
    void channelUpdated(ChannelUID channelUID, Data data);
}
