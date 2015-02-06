/**
 * 
 */
package name.zhangmin.gw.io.rest.resources;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.apps.AppFactory;
import name.zhangmin.gw.core.apps.AppRegistry;
import name.zhangmin.gw.core.apps.impl.ManagedAppProvider;
import name.zhangmin.gw.core.thing.ManagedThingProvider;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.ThingRegistry;
import name.zhangmin.gw.core.thing.link.AppChannelLinkRegistry;
import name.zhangmin.gw.core.thing.link.ManagedAppChannelLinkProvider;
import name.zhangmin.gw.core.thing.uid.ThingTypeUID;
import name.zhangmin.gw.core.thing.uid.ThingUID;
import name.zhangmin.gw.io.rest.RESTResource;
import name.zhangmin.gw.io.rest.resources.beans.ThingBean;
import name.zhangmin.gw.io.rest.util.BeanMapper;

/**
 * This class acts as a REST resource for things and is registered with
 * the Jersey servlet.
 * 
 * @author ZhangMin.name
 *
 */
@Path("things")
public class ThingResource implements RESTResource{
	
	private final Logger logger = LoggerFactory.getLogger(ThingResource.class);
	
	private AppChannelLinkRegistry appChannelLinkRegistry;
	private AppFactory appFactory;
	private AppRegistry appRegistry;
	private ManagedAppProvider managedAppProvider;
	private ManagedThingProvider managedThingProvider;
	private ManagedAppChannelLinkProvider managedAppChannelLinkProvider;
	private static ThingRegistry thingRegistry;
	
	@Context
	private UriInfo uriInfo;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(ThingBean thingBean) throws IOException {
		ThingUID thingUIDObject = new ThingUID(thingBean.UID);
		ThingTypeUID thingTypeUID = new ThingTypeUID(thingUIDObject.getThingTypeId());
		Configuration configuration = new Configuration();
		
		managedThingProvider.createThing(thingTypeUID, thingUIDObject, configuration);
		
		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
//		Collection<Thing> things = thingRegistry.getThings();
//		Set<ThingBean> thingBeans = convertToListBean(things);
//		
//		return Response.ok(thingBeans).build();
		return Response.ok("hello things").build();
		
	}
	
	@GET
	@Path("/{thingUID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUID(@PathParam("thingUID") String thingUID) {
		Thing thing = thingRegistry.getByUID(new ThingUID(thingUID));
		
		if (thing != null) {
			return Response.ok(BeanMapper.mapThingToBean(thing, appChannelLinkRegistry)).build();
		} else {
			return Response.noContent().build();
		}
	}
	
	protected void setAppChannelLinkRegistry(
			AppChannelLinkRegistry appChannelLinkRegistry) {
		this.appChannelLinkRegistry = appChannelLinkRegistry;
	}
	
	protected void unsetAppChannelLinkRegistry() {
		this.appChannelLinkRegistry = null;
	}
	
	protected void setAppFactory(AppFactory appFactory) {
		this.appFactory = appFactory;
	}
	protected void unsetAppFactory() {
		this.appFactory = null;
	}
	
	protected void setAppRegistry(AppRegistry appRegistry) {
		this.appRegistry = appRegistry;
	}
	protected void unsetAppRegistry() {
		this.appRegistry = null;
	}
	
	protected void setManagedAppProvider(ManagedAppProvider managedAppProvider) {
		this.managedAppProvider = managedAppProvider;
	}
	protected void unsetManagedAppProvider() {
		this.managedAppProvider = null;
	}
	
	protected void setThingRegistry(ThingRegistry thingRegistry) {
		this.thingRegistry = thingRegistry;
	}
	protected void unsetThingRegistry() {
		this.thingRegistry = null;
	}
	
	protected void setManagedThingProvider(ManagedThingProvider managedThingProvider) {
		this.managedThingProvider = managedThingProvider;
	}
	protected void unsetManagedThingProvider() {
		this.managedThingProvider = null;
	}
	
	protected void setManagedAppChannelLinkProvider(ManagedAppChannelLinkProvider managedAppChannelLinkProvider) {
		this.managedAppChannelLinkProvider = managedAppChannelLinkProvider;
	}
	
	protected void unsetManagedAppChannelLinkProvider(ManagedAppChannelLinkProvider managedAppChannelLinkProvider) {
		this.managedAppChannelLinkProvider = null;
	}
	
	 private Set<ThingBean> convertToListBean(Collection<Thing> things) {
	        Set<ThingBean> thingBeans = new LinkedHashSet<>();
	        for (Thing thing : things) {
	            ThingBean thingBean = BeanMapper.mapThingToBean(thing, appChannelLinkRegistry);
	            thingBeans.add(thingBean);
	        }
	        return thingBeans;
	    }
}
