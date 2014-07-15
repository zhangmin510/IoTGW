/**
 * 
 */
package name.zhangmin.gw.io.servicediscovery;

/**
 * This interface defines how to use JmDNS based service discovery
 * to register an unregister services on Bonjour/MDNS
 * 
 * @author ZhangMin.name
 *
 */
public interface DiscoveryService {
	/**
	 * This method register a service to be announced through
	 * Bonjour/MDNS
	 * 
	 * @param description the {@link ServiceDescription} instance with all details
	 * to identify the service
	 */
	public void registerService(ServiceDescription description);
	/**
	 * This method unregisters a service not to be announced through Bonjour/MDNS
	 * 
	 * @param description the {@link ServiceDescription} instance with all details
	 * to identify the service
	 */
	public void unregisterService(ServiceDescription description);
}
