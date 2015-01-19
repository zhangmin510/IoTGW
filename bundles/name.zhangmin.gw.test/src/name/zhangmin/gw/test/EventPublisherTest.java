/**
 * 
 */
package name.zhangmin.gw.test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import name.zhangmin.gw.core.event.EventPublisher;
import name.zhangmin.gw.core.lib.type.impl.StringType;


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
	
	public void unsetEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = null;
		System.out.println("unset eventPublisher " + this.eventPublisher);
	}
	
	public void activate() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				sendEvent();
				
			}
			
		}, 0, 60000);
	}
	
	public void sendEvent() {
		StringType s = new StringType();
		s.setState(new Date().toString());
		System.out.println("Posting test state at time: " + new Date() + "  ======>");
		eventPublisher.postState("test", s);
		
	}

}
