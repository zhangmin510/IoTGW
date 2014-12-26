/**
 * 
 */
package name.zhangmin.gw.test;

import name.zhangmin.gw.core.event.EventPublisher;
import name.zhangmin.gw.core.lib.type.impl.StateImpl;

/**
 * @author ZhangMin.name
 *
 */
public class EventPublisherTest {
	private EventPublisher eventPublisher;
	
	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
		System.out.println("set eventPublisher " + this.eventPublisher);
	}
	
	
	public void start() {
		eventPublisher.postState("test", new StateImpl());
		System.out.println("post test state");
	}

}
