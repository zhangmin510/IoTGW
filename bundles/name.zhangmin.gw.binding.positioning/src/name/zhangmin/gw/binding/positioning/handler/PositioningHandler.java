/**
 * 
 */
package name.zhangmin.gw.binding.positioning.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.binding.BaseThingHandler;
import name.zhangmin.gw.core.thing.uid.ChannelUID;

/**
 * @author ZhangMin.name
 *
 */
public class PositioningHandler extends BaseThingHandler{
	
	private Logger logger = LoggerFactory.getLogger(PositioningHandler.class);
	
	
	public PositioningHandler(Thing thing) {
		super(thing);
	}
	
	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {
		
	}

	@Override
	public void dispose() {
		logger.debug("Handler disposed,Unregister listener");
		
	}

	@Override
	public void initialize() {
		logger.debug("Handler initialized,Register listener");
		System.out.println("Positioning handler started, start to receive data");
		this.updateState(new ChannelUID("positioning:udp"), null);
	}
	
	
}
