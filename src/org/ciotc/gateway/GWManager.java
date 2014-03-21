/**
 *
 * JettyExample.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
/**
 * @author ZhangMin.name
 *
 */
public class GWManager{
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server  = new Server(8080);
		//Set webapp context
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setResourceBase("web");
		
		server.setHandler(webapp);
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
