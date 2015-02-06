/**
 * 
 */
package name.zhangmin.gw.io.rest.resources;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import name.zhangmin.gw.core.thing.link.AppChannelLink;
import name.zhangmin.gw.core.thing.link.AppChannelLinkRegistry;
import name.zhangmin.gw.core.thing.link.ManagedAppChannelLinkProvider;
import name.zhangmin.gw.core.thing.uid.ChannelUID;
import name.zhangmin.gw.io.rest.RESTResource;
import name.zhangmin.gw.io.rest.resources.beans.AppChannelLinkBean;

/**
 * @author ZhangMin.name
 *
 */
@Path("links")
public class AppChannelLinkResource implements RESTResource{
	private static AppChannelLinkRegistry appChannelLinkRegistry;
	private static ManagedAppChannelLinkProvider managedAppLinkChannelProvider;
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Collection<AppChannelLinkBean> getAll() {
		//TODO remove after test 
		managedAppLinkChannelProvider.addAppChannelLink(new AppChannelLink("testApp", new ChannelUID("bid:typeid:thingid:testChannelUID")));
		Collection<AppChannelLink> links = appChannelLinkRegistry.getAppChannelLinks();
		return toBeans(links);
	}
	
	@PUT
	@Path("/{appName}/{channelUID}")
	public Response link(@PathParam("appName")String appName, @PathParam("channelUID") String channelUID) {
		managedAppLinkChannelProvider.addAppChannelLink(new AppChannelLink(appName, new ChannelUID(channelUID)));
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{appName}/{channelUID}")
	public Response unlink(@PathParam("appName") String appName, @PathParam("channelUID") String channelUID) {
	     managedAppLinkChannelProvider.removeAppChannelLink(new AppChannelLink(appName, new ChannelUID(channelUID)));
	     return Response.ok().build();
	}
	
	public void setAppChannelLinkRegistry(
			AppChannelLinkRegistry appChannelLinkRegistry) {
		this.appChannelLinkRegistry = appChannelLinkRegistry;
	}
	public void setManagedAppLinkChannelProvider(
			ManagedAppChannelLinkProvider managedAppLinkChannelProvider) {
		this.managedAppLinkChannelProvider = managedAppLinkChannelProvider;
	}
	public void unsetAppChannelLinkRegistry(
			AppChannelLinkRegistry appChannelLinkRegistry) {
		this.appChannelLinkRegistry = null;
	}
	public void unsetManagedAppLinkChannelProvider(
			ManagedAppChannelLinkProvider managedAppLinkChannelProvider) {
		this.managedAppLinkChannelProvider = null;
	}
	
	private Collection<AppChannelLinkBean> toBeans(Collection<AppChannelLink> links) {
		Collection<AppChannelLinkBean> beans = new ArrayList<>();
		for (AppChannelLink link : links) {
			AppChannelLinkBean bean = new AppChannelLinkBean(link.getAppName(), link.getChannelUID().toString());
			beans.add(bean);
		}
		return beans;
		
	}
	
}
