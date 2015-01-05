package name.zhangmin.gw.web;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebActivator{

	private final Logger logger = LoggerFactory.getLogger(WebActivator.class);
	private BundleContext bundleContext;
	private HttpService httpService;
	
	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}
	
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	public void start() {
		
		logger.debug("IotGw Web module started ...");
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


	public void stop(){
		
		logger.debug("IotGw Web module stopped.");
		httpService.unregister("/web");
	}

}
