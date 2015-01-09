/**
 * 
 */
package name.zhangmin.gw.io.rest.resources;

import java.util.Collection;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.core.thing.type.ThingType;
import name.zhangmin.gw.core.thing.type.ThingTypeProvider;
import name.zhangmin.gw.core.thing.type.ThingTypeRegistry;
import name.zhangmin.gw.io.rest.RESTResource;

/**
 * @author ZhangMin.name
 *
 */
@Path(AppResource.PATH_APPS)
public class AppResource implements RESTResource{
	
	private final Logger logger = LoggerFactory.getLogger(AppResource.class);
	
	public static final String PATH_APPS = "apps";
	
	private static ThingTypeProvider thingTypeProvider;
	private ThingTypeRegistry thingTypeRegistry;
	
    @Context 
    UriInfo uriInfo;
    
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		
		System.out.println(thingTypeProvider);
		 Collection<ThingType> tts = thingTypeProvider.getThingTypes();
			Iterator it = tts.iterator();
			while (it.hasNext()) {
				System.out.println((ThingType)it.next());
			}
		return Response.ok("hello").build();
	}
	
	
	protected void setThingTypeProvider(ThingTypeProvider thingTypeProvider) {
	    this.thingTypeProvider = thingTypeProvider;  
	}

	protected void unsetThingTypeProvider(ThingTypeProvider thingTypeProvider) {
	     this.thingTypeProvider = null;
	}
	
	protected void setThingTypeRegistry(ThingTypeRegistry thingTypeRegistry) {
	    this.thingTypeRegistry = thingTypeRegistry;
	}

	protected void unsetThingTypeRegistry(ThingTypeRegistry thingTypeRegistry) {
	     this.thingTypeRegistry = null;
	}
	
}
