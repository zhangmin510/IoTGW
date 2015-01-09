/**
 * 
 */
package name.zhangmin.gw.io.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.io.rest.RESTResource;

/**
 * @author ZhangMin.name
 *
 */
@Path("hello")
public class TestResource implements RESTResource{
	
	private final Logger logger = LoggerFactory.getLogger(TestResource.class);
	
	public static final String PATH_APPS = "hello";
	
    @Context 
    UriInfo uriInfo;
    
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response test() {
		System.out.println("Getting " + PATH_APPS + "....");
		return Response.ok("hello hello").build();
	}
}
