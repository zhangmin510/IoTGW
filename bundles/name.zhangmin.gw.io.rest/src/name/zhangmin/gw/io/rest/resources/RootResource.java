package name.zhangmin.gw.io.rest.resources;

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

import com.sun.jersey.api.json.JSONWithPadding;

import name.zhangmin.gw.io.rest.MediaTypeHelper;
import name.zhangmin.gw.io.rest.RESTApplication;
import name.zhangmin.gw.io.rest.RESTResource;
import name.zhangmin.gw.io.rest.resources.beans.RootBean;

/**
 * This class acts as an entry point / root resource for the REST API.
 * <p>
 * In good HATEOAS manner, it provides links to other offered resources.
 * <p>
 * The result is returned as XML or JSON
 * <p>
 * This resources is registered with the Jersey servlet.
 * @author ZhangMin.name
 *
 */
@Path("/")
public class RootResource {
	@Context UriInfo uriInfo;
	
	@GET
	@Produces ({MediaType.WILDCARD })
	public Response getRoot(
			@Context HttpHeaders headers,
			@QueryParam("type") String type,
			@QueryParam("jsoncallback") @DefaultValue("callback") String callback) {
		String responseType = MediaTypeHelper.getResponseMediaType(headers.getAcceptableMediaTypes(), type);
		if (responseType != null) {
			Object responseObject = responseType.equals(MediaTypeHelper.APPLICATION_X_JAVASCRIPT) ?
					new JSONWithPadding(getRootBean(), callback) : getRootBean();
					return Response.ok(responseObject, responseType).build();
		} else {
			return Response.notAcceptable(null).build();
		}
	}
	
	private RootBean getRootBean() {
		RootBean bean = new RootBean();
		for (RESTResource resource : RESTApplication.getRestResources()) {
			String path = resource.getClass().getAnnotation(Path.class).value();
			bean.links.put(path, uriInfo.getBaseUriBuilder().path(path).build().toASCIIString());
		}
		return bean;
	}
	
	
	
}
