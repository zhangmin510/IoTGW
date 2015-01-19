/**
 * 
 */
package name.zhangmin.gw.web;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ZhangMin.name
 *
 */
public class WebConsole {
	private final Logger logger = LoggerFactory.getLogger(WebConsole.class);
	private HttpService httpService;
	
	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}
	public void unsetHttpService(HttpService httpService) {
		this.httpService = httpService;
	}
	public void activate(ComponentContext context) {
//		ServiceReference<?> sr = bundleContext.getServiceReference(HttpService.class.getName());
//		httpService = (HttpService) bundleContext.getService(sr);
		HttpContext httpContext = httpService.createDefaultHttpContext();
		Dictionary<String, String> servletParams = new Hashtable<String, String>();
		if (httpService != null) {
			try {
			httpService.registerResources("/web", "WebRoot", httpContext);
			httpService.registerServlet("/web/console", new ConsoleServlet(), servletParams, httpContext);
			
			} catch (Exception e) {
				logger.error("Exception occured when register servlet or resources, " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.error("The Http Service is null");
		}
	}
	
	public void deactivate(ComponentContext context) {
		logger.debug("IotGw Web module stopped.");
		httpService.unregister("/web");
	}
}
