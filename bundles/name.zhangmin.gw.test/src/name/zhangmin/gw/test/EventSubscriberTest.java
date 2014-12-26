/**
 * 
 */
package name.zhangmin.gw.test;

import name.zhangmin.gw.core.event.impl.AbstractEventSubscriber;
import name.zhangmin.gw.core.lib.type.State;

/**
 * @author ZhangMin.name
 *
 */
public class EventSubscriberTest extends AbstractEventSubscriber{
	
	@Override
	public void receiveState(String appName, State state) {
		// TODO Auto-generated method stub
		System.out.println("Rcv :" + appName + state.toString());
	}

}
