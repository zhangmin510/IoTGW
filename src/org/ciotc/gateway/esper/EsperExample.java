/**
 *
 * EsperExample.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway.esper;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * @author ZhangMin.name
 *
 */
public class EsperExample {
	public EsperExample(){
	}
	public void run(){
		//Obtain engine instance and register a continuous query
		Configuration config = new Configuration();
		config.addEventTypeAutoName("org.ciotc.gateway.esper");
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
		String exp = "select avg(num) from TestEvent.win:time(30 sec)";
		EPStatement statement = epService.getEPAdministrator().createEPL(exp);
		//Add updateListener
		statement.addListener(new MyListener());
		//Send event
		TestEvent event = new TestEvent("zhangmin",5);
		TestEvent event1 = new TestEvent("zhangmin",6);
		epService.getEPRuntime().sendEvent(event);
		epService.getEPRuntime().sendEvent(event1);
	}
	private class MyListener implements UpdateListener{

		@Override
		public void update(EventBean[] newEvents, EventBean[] oldEvents) {
			EventBean event = newEvents[0];
			System.out.println("Avg=" + event.get("avg(num)"));
		}
		
	}
	public static void main(String[] args) {
		EsperExample esper = new EsperExample();
		esper.run();
		

	}

}
