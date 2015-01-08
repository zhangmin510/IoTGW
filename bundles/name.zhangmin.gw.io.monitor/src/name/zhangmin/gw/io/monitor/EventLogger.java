package name.zhangmin.gw.io.monitor;

import name.zhangmin.gw.core.event.impl.AbstractEventSubscriber;
import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.lib.type.State;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLogger extends AbstractEventSubscriber {

	private final Logger logger = LoggerFactory.getLogger("runtime.busevents");

	public void receiveCommand(String itemName, Command command) {
		logger.info("{} received command {}", itemName, command);
	}

	public void receiveUpdate(String itemName, State newStatus) {
		logger.info("{} state updated to {}", itemName, newStatus);
	}

}
