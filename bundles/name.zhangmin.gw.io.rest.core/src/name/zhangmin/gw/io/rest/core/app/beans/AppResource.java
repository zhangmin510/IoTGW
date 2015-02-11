/**
 * 
 */
package name.zhangmin.gw.io.rest.core.app.beans;

import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.io.rest.RESTResource;

/**
 * @author ZhangMin.name
 *
 */
@Path(AppResource.PATH_APPS)
public class AppResource implements RESTResource{
	
	private final Logger logger = LoggerFactory.getLogger(AppResource.class);
	
	public static final String PATH_APPS = "apps";

}
