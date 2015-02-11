/**
 * 
 */
package name.zhangmin.gw.io.rest.core;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import name.zhangmin.gw.io.rest.RESTResource;

/**
 * @author ZhangMin.name
 *
 */
@Path("/things")
public class ThingResource implements RESTResource{
    @Context 
    UriInfo uriInfo;
	
	@GET
	@Produces ({MediaType.WILDCARD })
	public Response getAll() {
		return Response.notAcceptable(null).build();
	}
}
