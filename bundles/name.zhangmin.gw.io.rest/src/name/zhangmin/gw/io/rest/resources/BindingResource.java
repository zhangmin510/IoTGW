/**
 * 
 */
package name.zhangmin.gw.io.rest.resources;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import name.zhangmin.gw.core.thing.binding.BindingInfo;
import name.zhangmin.gw.core.thing.binding.BindingInfoRegistry;
import name.zhangmin.gw.io.rest.RESTResource;
import name.zhangmin.gw.io.rest.resources.beans.BindingInfoBean;
import name.zhangmin.gw.io.rest.resources.beans.ThingTypeBean;

/**
 * @author ZhangMin.name
 *
 */
@Path("bindings")
public class BindingResource implements RESTResource{
	private static BindingInfoRegistry bindingInfoRegistry;
	private static ThingTypeResource thingTypeResource;
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Set<BindingInfoBean> getAll() {
		List<BindingInfo> bindingInfos = bindingInfoRegistry.getBindingInfos();
		Set<BindingInfoBean> bindingInfoBeans = covertToBeans(bindingInfos);
		return bindingInfoBeans;
	}
	
	private BindingInfoBean covertToBean(BindingInfo bindingInfo) {
		Set<ThingTypeBean> thingTypeBeans = thingTypeResource.getThingTypeBeans(bindingInfo.getId());
		return new BindingInfoBean(bindingInfo.getId(), bindingInfo.getName(), bindingInfo.getAuthor(),
				bindingInfo.getDescription(), thingTypeBeans);
	}
	
	private Set<BindingInfoBean> covertToBeans(List<BindingInfo> bindingInfos) {
		Set<BindingInfoBean> ret = new LinkedHashSet<BindingInfoBean>();
		for (BindingInfo bi : bindingInfos) {
			ret.add(covertToBean(bi));
		}
		return ret;
	}
	
	
	public void setBindingInfoRegistry(
			BindingInfoRegistry bindingInfoRegistry) {
		BindingResource.bindingInfoRegistry = bindingInfoRegistry;
	}
	public void setThingTypeResource(ThingTypeResource thingTypedResource) {
		BindingResource.thingTypeResource = thingTypedResource;
	}
	public void unsetBindingInfoRegistry(
			BindingInfoRegistry bindingInfoRegistry) {
		BindingResource.bindingInfoRegistry = null;
	}
	public void unsetThingTypeResource(ThingTypeResource thingTypedResource) {
		BindingResource.thingTypeResource = null;
	}
	
	
}
