/**
 * 
 */
package name.zhangmin.gw.io.servicediscovery;

import java.util.Hashtable;


/**
 * This is a simple data container to keep all details of a service
 * description together.
 * 
 * @author ZhangMin.name
 *
 */
public class ServiceDescription {
	public String serviceType;
	public String serviceName;
	public int servicePort;
	public Hashtable<String, String> serviceProperties;
	
	/**
	 * Constructor for a {@link ServiceDescription}, which takes all details as parameters
	 * 
	 * @param serviceType String service type, like "_ioygw._tcp.local."
	 * @param serviceName String service name, like "IoTGW"
	 * @param servicePort Int service port, like 8080
	 * @param serviceProperties Hashtable service props, like url = "/rest"
	 * 
	 */
	public ServiceDescription(String serviceType, String serviceName, int servicePort,
			Hashtable<String, String> serviceProperties) {
		this.serviceName = serviceName;
		this.servicePort = servicePort;
		this.serviceType = serviceType;
		this.serviceProperties = serviceProperties;
	}
}
