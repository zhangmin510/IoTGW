/**
 * 
 */
package name.zhangmin.gw.test;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * @author ZhangMin.name
 *
 */
public class EventDumper implements EventHandler{

	@Override
	public void handleEvent(Event arg0) {
		System.out.println("===========Event Dumper===========");
		System.out.println("Recevie Topic: " + arg0.getTopic() );
		for (String s : arg0.getPropertyNames()) {
			System.out.println(s + "=" + arg0.getProperty(s));
		}
		System.out.println("==================================");
	}

}
