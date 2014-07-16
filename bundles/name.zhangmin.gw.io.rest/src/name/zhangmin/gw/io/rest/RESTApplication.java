/**
 * 
 */
package name.zhangmin.gw.io.rest;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import name.zhangmin.gw.core.event.EventPublisher;
import name.zhangmin.gw.io.rest.resources.RootResource;
import name.zhangmin.gw.io.servicediscovery.DiscoveryService;
import name.zhangmin.gw.io.servicediscovery.ServiceDescription;

import com.sun.jersey.core.util.FeaturesAndProperties;
import com.sun.jersey.spi.container.servlet.ServletContainer;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main component of REST API; it gets all required services
 * injected, registers itself as a servlet on the HTTP service and adds
 * the different rest resources to this service.
 * @author ZhangMin.name
 *
 */
public class RESTApplication extends Application{
	public static final String REST_SERVLET_ALIAS = "/rest";
	
	private static final Logger logger = LoggerFactory.getLogger(RESTApplication.class);
	
	private int httpSSLPort;
	private int httpPort;
	
	private String mdnsName;
	
	private HttpService httpService;
	private DiscoveryService discoveryService;
	static private EventPublisher eventPublisher;
//	static private AppRegistry appRegistry;
	static private List<RESTResource> restResources = new ArrayList<RESTResource>();
	
	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}
	
	public void unsetHttpService(HttpService httpService) {
		this.httpService = null;
	}

	public void setEventPublisher(EventPublisher eventPublisher) {
		RESTApplication.eventPublisher = eventPublisher;
	}
	
	public void unsetEventPublisher(EventPublisher eventPublisher) {
		RESTApplication.eventPublisher = null;
	}

	static public EventPublisher getEventPublisher() {
		return eventPublisher;
	}

//	public void setItemRegistry(ItemRegistry itemRegistry) {
//		RESTApplication.itemRegistry = itemRegistry;
//	}
//	
//	public void unsetItemRegistry(ItemRegistry itemRegistry) {
//		RESTApplication.itemRegistry = null;
//	}
//
//	static public ItemRegistry getItemRegistry() {
//		return RESTApplication.itemRegistry;
//	}
//
	public void setDiscoveryService(DiscoveryService discoveryService) {
		this.discoveryService = discoveryService;
	}
	
	public void unsetDiscoveryService(DiscoveryService discoveryService) {
		this.discoveryService = null;
	}

	public void addRESTResource(RESTResource resource) {
		RESTApplication.restResources.add(resource);
	}

	public void removeRESTResource(RESTResource resource) {
		RESTApplication.restResources.remove(resource);
	}
	
	public void activate(BundleContext bundleContext) {
		
		
		try {
			ServletContainer servletContainer = new ServletContainer(this);
			httpService.registerServlet(
					REST_SERVLET_ALIAS, servletContainer, getJerseyServletParams(), createHttpContext());
			
			logger.info("Started REST API at " + REST_SERVLET_ALIAS);
			if (discoveryService != null) {
				mdnsName = bundleContext.getProperty("mdnsName");
				if (mdnsName == null) mdnsName = "iotgw";
				try {
					httpPort = Integer.parseInt(bundleContext.getProperty("jetty.port"));
					discoveryService.registerService(getDefaultServiceDescription());
				} catch(NumberFormatException e) {
					logger.error("jetty.port is not a number");
				}
				try {
					httpSSLPort = Integer.parseInt(bundleContext.getProperty("jetty.port.ssl"));
					discoveryService.registerService(getSSLServiceDescription());
				} catch(NumberFormatException e) {
					logger.error("jetty.port.ssl is not a number");
				}
				
			}
			
	
		} catch (ServletException se) {
			throw new RuntimeException(se);
		} catch (NamespaceException se) {
			throw new RuntimeException(se);
		}
		
	}
	public void deactivate() {
		if (this.httpService != null) {
			httpService.unregister(REST_SERVLET_ALIAS);
			logger.info("Stooped RESTAPI");
		}
		
		if (this.discoveryService != null) {
			this.discoveryService.unregisterService(getDefaultServiceDescription());
			this.discoveryService.unregisterService(getSSLServiceDescription());
		}
		restResources.clear();
	}
	
	protected HttpContext createHttpContext() {
		HttpContext defaultHttpContext = httpService.createDefaultHttpContext();
		return defaultHttpContext;
	}
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> result = new HashSet<Class<?>>();
		result.add(RootResource.class);
		for (RESTResource res : restResources) {
			result.add(res.getClass());
		}
		return result;
	}
	
	public static List<RESTResource> getRestResources() {
		return restResources;
	}
	
	private Dictionary<String, String> getJerseyServletParams() {
		Dictionary<String, String> jerseyServletParams = new Hashtable<String, String>();
		jerseyServletParams.put("javax.ws.rs.Application", RESTApplication.class.getName());
		jerseyServletParams.put(FeaturesAndProperties.FEATURE_XMLROOTELEMENT_PROCESSING, "true");
		return jerseyServletParams;
	}
	
	private ServiceDescription getDefaultServiceDescription() {
		Hashtable<String, String> serviceProperties = new Hashtable<String, String>();
		serviceProperties.put("uri", REST_SERVLET_ALIAS);
		return new ServiceDescription("_" + mdnsName + "-server._tcp.local.", mdnsName, httpPort, serviceProperties);
	}
    private ServiceDescription getSSLServiceDescription() {
    	ServiceDescription description = getDefaultServiceDescription();
    	description.serviceType = "_" + mdnsName + "-server-ssl._tcp.local.";
    	description.serviceName = "" + mdnsName + "-ssl";
		description.servicePort = httpSSLPort;
		return description;
    }
	

	
}
