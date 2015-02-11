/**
 * 
 */
package name.zhangmin.gw.io.rest.resources;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.apps.AppFactory;
import name.zhangmin.gw.core.apps.AppNotFoundException;
import name.zhangmin.gw.core.apps.AppRegistry;
import name.zhangmin.gw.core.apps.GenericApp;
import name.zhangmin.gw.core.apps.impl.ManagedAppProvider;
import name.zhangmin.gw.core.thing.Channel;
import name.zhangmin.gw.core.thing.ManagedThingProvider;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.ThingRegistry;
import name.zhangmin.gw.core.thing.ThingStatus;
import name.zhangmin.gw.core.thing.link.AppChannelLink;
import name.zhangmin.gw.core.thing.link.AppChannelLinkRegistry;
import name.zhangmin.gw.core.thing.link.ManagedAppChannelLinkProvider;
import name.zhangmin.gw.core.thing.uid.ChannelUID;
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
	@Consumes(MediaType.APPLICATION_XML)
	public Response create(ThingBean thingBean) throws IOException {
		
		ThingUID thingUIDObject = new ThingUID(thingBean.UID);
		ThingTypeUID thingTypeUID = new ThingTypeUID(thingUIDObject.getThingTypeId());
		Configuration configuration = new Configuration();
		
		managedThingProvider.createThing(thingTypeUID, thingUIDObject, configuration);
		
		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Set<ThingBean> getAll() {
		
		//TODO Remove after test
		//Problem in create thing logic
//		ThingUID thingUIDObject = new ThingUID("positioning", "positioning:udp", "test");
//		ThingTypeUID thingTypeUID = new ThingTypeUID(thingUIDObject.getThingTypeId());
//		Configuration configuration = new Configuration();
//		managedThingProvider.createThing(thingTypeUID, thingUIDObject, configuration);
		
		Collection<Thing> things = thingRegistry.getThings();
		Set<ThingBean> thingBeans = convertToListBean(things);
		
		return thingBeans;
		
	}
	
	@GET
	@Path("/{thingUID}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getByUID(@PathParam("thingUID") String thingUID) {
		Thing thing = thingRegistry.getByUID(new ThingUID(thingUID));
		
		if (thing != null) {
			return Response.ok(BeanMapper.mapThingToBean(thing, appChannelLinkRegistry)).build();
		} else {
			return Response.noContent().build();
		}
	}
	
	@POST
	@Path("/{thingUID}/channels/{channelId}/link")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response link(@PathParam("thingUID") String thingUID, @PathParam("channelId") String channelId, String appName) {
		Thing thing = thingRegistry.getByUID(new ThingUID(thingUID));
		if (thing == null) {
			logger.info("Received HTTP POST reqeust at '{}' for the unknown thing '{}'.", uriInfo.getPath(), thingUID);
			return Response.status(Status.NOT_FOUND).build();
		}
		Channel channel = findChannel(channelId, thing);
		if (channel == null) {
			logger.info("Received HTTP POST request at '{}' for the unknown channel '{}' of the thing '{}'."
					,uriInfo.getPath(),channel, thingUID);
			return Response.status(Status.NOT_FOUND).build();
		}
		
		try {
			appRegistry.getApp(appName);
		} catch (AppNotFoundException e) {
			GenericApp app = appFactory.createApp(channel.getAcceptedAppType(), appName);
			managedAppProvider.addApp(app);
		}
		
		ChannelUID channelUID = new ChannelUID(new ThingUID(thingUID), channelId);
		unlinkChannelIfAlreadyBound(channelUID);
		managedAppChannelLinkProvider.addAppChannelLink(new AppChannelLink(appName, channelUID));
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{thingUID}")
	public Response remove(@PathParam("thingUID")String thingUID) {
		if (managedThingProvider.removeThing(new ThingUID(thingUID)) == null) {
			logger.info("Received HTTP DELETE request at '{}' for unknown thing '{}'.", uriInfo.getPath(), thingUID);
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{thingUID}/channels/{channelId}/link")
	public Response unlink(@PathParam("thingUID")String thingUID, @PathParam("channelId") String channelId, String appName) {
		ChannelUID channelUID = new ChannelUID(new ThingUID(thingUID), channelId);
		String boundApp = appChannelLinkRegistry.getBoundApp(channelUID);
		if (boundApp != null) {
			managedAppChannelLinkProvider.removeAppChannelLink(new AppChannelLink(boundApp, channelUID));
		}
		return Response.ok().build();
	}
	
	@PUT
	@Path("/{thingUID}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("thingUID")String thingUID, ThingBean thingBean) throws IOException {
		ThingUID thingUIDObject = new ThingUID(thingUID);
		
		Thing thing = thingRegistry.getByUID(thingUIDObject);
		if (thing == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		for (Entry<String, Object> entry : thingBean.configuration.entrySet()) {
			thing.getConfiguration().put(entry.getKey(), entry.getValue());
		}
		managedThingProvider.addThing(thing);
		return Response.ok().build();
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
	 private Channel findChannel(String channelId, Thing thing) {
		 for (Channel channel : thing.getChannels()) {
			 if (channel.getUID().getId().equals(channelId)) {
				 return channel;
			 }
		 }
		 return null;
	 }
	 
	 private void unlinkChannelIfAlreadyBound(ChannelUID channelUID) {
		 Collection<AppChannelLink> links = managedAppChannelLinkProvider.getAppChannelLinks();
		 for (AppChannelLink link : links) {
			 if (link.getChannelUID().equals(channelUID)) {
				 logger.info("Channel '{}' is already linked to app '{}' and will be unlinked before it will be linked to the new app.",
						 channelUID, link.getAppName());
				 managedAppChannelLinkProvider.removeAppChannelLink(new AppChannelLink(link.getAppName(),link.getChannelUID()));
			 }
		 }
	 }
}
