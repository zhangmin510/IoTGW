/**
 * 
 */
package name.zhangmin.gw.io.servicediscovery;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
/**
 * This class starts the JmDNS and implements interface to register and ungister services.
 * 
 * @author ZhangMin.name
 *
 */
public class DiscoveryServiceImpl implements DiscoveryService{
	private static Logger logger = LoggerFactory.getLogger(
			DiscoveryServiceImpl.class);
	private JmDNS jmdns;
	
	public DiscoveryServiceImpl() {}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerService(ServiceDescription description) {
		ServiceInfo serviceInfo = ServiceInfo.create(description.serviceType, 
				description.serviceName, description.servicePort, 0, 0, 
				description.serviceProperties);
		
		try {
			logger.debug("Registering new service " + description.serviceType + " at port "
					+ String.valueOf(description.servicePort));
			jmdns.registerService(serviceInfo);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unregisterService(ServiceDescription description) {
		ServiceInfo serviceInfo = ServiceInfo.create(description.serviceType, 
				description.serviceName, description.servicePort, 0, 0, 
				description.serviceProperties);
		logger.debug("Unregistering service " + description.serviceType + " at port "
					+ String.valueOf(description.servicePort));
		jmdns.unregisterService(serviceInfo);
		
		
	}
	
	protected void ungisterAllServices() {
		jmdns.unregisterAllServices();
	}
	public void activate() {
		try {
			jmdns = JmDNS.create();
			logger.info("mDNS service has benn started ");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	public void deactivate() {
		ungisterAllServices();
		try {
			jmdns.close();
			logger.info("mDNS service has been stopped");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
}
