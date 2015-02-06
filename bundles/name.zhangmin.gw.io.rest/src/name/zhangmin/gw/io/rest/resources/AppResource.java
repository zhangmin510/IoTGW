/**
 * 
 */
package name.zhangmin.gw.io.rest.resources;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import name.zhangmin.gw.binding.positioning.app.PositioningApp;
import name.zhangmin.gw.core.apps.App;
import name.zhangmin.gw.core.apps.AppFactory;
import name.zhangmin.gw.core.apps.AppNotFoundException;
import name.zhangmin.gw.core.apps.AppRegistry;
import name.zhangmin.gw.core.apps.GenericApp;
import name.zhangmin.gw.core.apps.impl.ManagedAppProvider;
import name.zhangmin.gw.core.event.EventPublisher;
import name.zhangmin.gw.core.lib.type.State;
import name.zhangmin.gw.core.lib.type.impl.StringType;
import name.zhangmin.gw.io.rest.RESTResource;
import name.zhangmin.gw.io.rest.resources.beans.AppBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ZhangMin.name
 *
 */
@Path("apps")
public class AppResource implements RESTResource{
	
	private final Logger logger = LoggerFactory.getLogger(AppResource.class);
	private static ManagedAppProvider managedAppProvider;
	private static AppRegistry appRegistry;
	private static EventPublisher eventPublisher;
	private static Set<AppFactory> appFactories = new HashSet<>();
	
	
    @Context 
    UriInfo uriInfo;
    
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getApps() {
		logger.debug("Received HTTP Get request at '{}'", uriInfo.getPath());
		
		PositioningApp app = new PositioningApp("RFID TYPE", "positioning");
		managedAppProvider.addApp(app);
		
		return Response.ok(getAppBeans()).build();
	}
	
	@GET @Path("/{appname:[a-zA-Z_0-9]*}/state")
	@Produces({MediaType.TEXT_PLAIN})
	public Response getPlainAppState(@PathParam("appname") String appname) {
		App app = getApp(appname);
		if (app != null) {
			logger.debug("Received HTTP GET request at '{}'.", uriInfo.getPath());
			throw new WebApplicationException(Response.ok(app.getState().toString()).build());
		} else {
			logger.debug("Received HTTP GET request at '{}' for the unknown app '{}'.", uriInfo.getPath(), appname);
			throw new WebApplicationException(404);
		}
	}
	
	@GET @Path("{appname: [a-zA-Z_0-9]*}")
	@Produces ( {MediaType.WILDCARD})
	public Response getAppData(@PathParam("appname") String appname) {
		logger.debug("Received HTTP GET request at '{}'", uriInfo.getPath());
		throw new WebApplicationException(Response.ok(getAppBean(appname)).build());
	}
	
	@PUT @Path("/{appname: [a-zA-Z_0-9]*}/state")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response putAppState(@PathParam("appname") String appname, String value) {
		App app = getApp(appname);
		if (app != null) {
			//TODO check app#getSupportedState verify value	
			StringType state = new StringType();
			state.setState(value);
			if (state != null) {
				logger.debug("Received HTTP PUT request at '{}' with value '{}'.", uriInfo.getPath(), value);
				eventPublisher.postState(appname, state);
				return Response.ok().build();
			} else {
				logger.warn("Received HTTP PUT request at '{}' with an invalid status value '{}'.", uriInfo.getPath(), value);
				return Response.status(Status.BAD_REQUEST).build();
			}
		} else {
			logger.info("Received HTTP GET request at '{}' for the unknown app '{}'.", uriInfo.getPath(), appname);
			throw new WebApplicationException(404);
		}
	}
	
	@Context UriInfo localUriInfo;
	@POST @Path("/{appname: [a-zA-Z_0-9]*}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response postAppCommand(@PathParam("appname") String appname, String value) {
		App app = getApp(appname);
		throw new WebApplicationException(404);
	}
	
	@PUT @Path("/{appname: [a-zA-Z_0-9]*}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response createOrUpdate(@PathParam("appname") String appname, String appType) {
		GenericApp newApp = null;
		
		for (AppFactory appFactory : appFactories) {
			newApp = appFactory.createApp(appType, appname);
			if (newApp != null) break;
		}
		
		if (newApp == null) {
			logger.warn("Received HTTP PUT request at '{}' with an invalid app type '{}'.", uriInfo.getPath(), appType);
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		App existingApp = getApp(appname);
		
		if (existingApp == null) {
			managedAppProvider.addApp(newApp);
		} else {
			logger.warn("Cannot update exsiting app '{}', because is not managed.", appname);
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{/appname: [a-zA-Z_0-9]*}")
	public Response removeApp(@PathParam("appname")String appname) {
		if (managedAppProvider.removeApp(appname) == null) {
			logger.info("Received HTTP DELETE reqeust at '{}' for the unknown app '{}'.", uriInfo.getPath(), appname);
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().build();
	}
	
	protected void setManagedAppProvider(ManagedAppProvider managedAppProvider) {
		this.managedAppProvider = managedAppProvider;
	}
	protected void unsetManagedAppProvider() {
		this.managedAppProvider = null;
	}
	protected void setAppRegistry(AppRegistry appRegistry) {
	    this.appRegistry = appRegistry;  
	}

	protected void unsetAppRegistry(AppRegistry appRegistry) {
	     this.appRegistry = null;
	}
	
	protected void setEventPublisher(EventPublisher eventPublisher) {
	    this.eventPublisher = eventPublisher;
	}

	protected void unsetEventPublisher(EventPublisher eventPublisher) {
	     this.eventPublisher = null;
	}
	
	protected void setAppFactory(AppFactory appFactory) {
	    this.appFactories.add(appFactory);
	}

	protected void unsetAppFactory(AppFactory appFactory) {
	    this.appFactories.remove(appFactory);
	}
	
	private List<AppBean> getAppBeans() {
		List<AppBean> beans = new LinkedList<AppBean>();
		Collection<App> apps = appRegistry.getApps();
		
		for (App app : apps) {
			AppBean ab = new AppBean();
			ab.name = app.getName();
			ab.state = app.getState().toString();
			ab.type = app.getType();
			ab.link = UriBuilder.fromUri(uriInfo.getBaseUri().toASCIIString()).path("apps").path(ab.name).build().toASCIIString();
		}
		
		return beans;
		
	}
	
	private App getApp(String appname) {
		try {
			App app = appRegistry.getApp(appname);
			return app;
		} catch (AppNotFoundException ignored) {
			
		}
		return null;
		
	}
	
	private AppBean getAppBean(String appname) {
		App app = getApp(appname);
		if (app != null) {
			AppBean ab = new AppBean();
			ab.name = app.getName();
			ab.state = app.getState().toString();
			ab.type = app.getType();
			ab.link = UriBuilder.fromUri(uriInfo.getBaseUri().toASCIIString()).path("apps").path(ab.name).build().toASCIIString();
			return ab;
		} else {
			logger.info("Received HTTP GET request at '{}' for the unkonwn app '{}'.", uriInfo.getPath(), appname);
			throw new WebApplicationException(404);
		}
	}
	
	
}
