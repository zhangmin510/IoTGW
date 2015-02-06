/**
 * 
 */
package name.zhangmin.gw.io.rest.resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.zhangmin.gw.core.thing.type.ChannelDefinition;
import name.zhangmin.gw.core.thing.type.ThingType;
import name.zhangmin.gw.core.thing.type.ThingTypeRegistry;
import name.zhangmin.gw.core.thing.uid.ThingTypeUID;
import name.zhangmin.gw.io.rest.RESTResource;
import name.zhangmin.gw.io.rest.resources.beans.ChannelDefinitionBean;
import name.zhangmin.gw.io.rest.resources.beans.ThingTypeBean;

/**
 * @author ZhangMin.name
 *
 */
@Path("thing-types")
public class ThingTypeResource implements RESTResource{

	private Logger logger = LoggerFactory.getLogger(ThingTypeResource.class);
	
	private static ThingTypeRegistry thingTypeRegistry;
	
    @Context 
    UriInfo uriInfo;
    
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Set<ThingTypeBean> getAll() {

		Set<ThingTypeBean> thingTypes = convertToThingTypeBeans(thingTypeRegistry.getThingTypes());
		return thingTypes;
	}
	
	@GET
	@Path("/{thingTypeUID}")
	@Produces(MediaType.APPLICATION_XML)
	public ThingTypeBean getByUID(@PathParam("thingTypeUID") String thingTypeUID) {
		ThingType thingType = thingTypeRegistry.getThingType(new ThingTypeUID(thingTypeUID));
		if (thingType != null) {
			return convertToThingTypeBean(thingType);
		} else {
			return new ThingTypeBean();
		}
	}
	
    private Set<ThingTypeBean> convertToThingTypeBeans(List<ThingType> thingTypes) {
        Set<ThingTypeBean> thingTypeBeans = new HashSet<>();

        for (ThingType thingType : thingTypes) {
            thingTypeBeans.add(convertToThingTypeBean(thingType));
        }

        return thingTypeBeans;
    }
    
    private List<ChannelDefinitionBean> convertToChannelDefinitionBeans(List<ChannelDefinition> channelDefinitions) {
    	List<ChannelDefinitionBean> channelDefinitionBeans = new ArrayList<>();
    	for (ChannelDefinition channelDefinition : channelDefinitions) {
    		channelDefinitionBeans.add(covertToChannelDefinitionBean(channelDefinition));
    	}
    	return channelDefinitionBeans;
    	
    }
    
    private ChannelDefinitionBean covertToChannelDefinitionBean(ChannelDefinition cd) {
    	return new ChannelDefinitionBean(cd.getId(), cd.getType().getDescription(), cd.getType().getLabel() );
    }
    private ThingTypeBean convertToThingTypeBean(ThingType thingType) {
    	return new ThingTypeBean(thingType.getUID().toString(), thingType.getManufacturer(), thingType.getLabel()
    			, thingType.getDescription(),convertToChannelDefinitionBeans(thingType.getChannelDefinitions()));
    }
    
    public Set<ThingTypeBean> getThingTypeBeans(String bindingId) {

        List<ThingType> thingTypes = thingTypeRegistry.getThingTypes(bindingId);
        Set<ThingTypeBean> thingTypeBeans = convertToThingTypeBeans(thingTypes);
        return thingTypeBeans;
    }
    
    protected void setThingTypeRegistry(ThingTypeRegistry thingTypeRegistry) {
	    this.thingTypeRegistry = thingTypeRegistry;
	}

	protected void unsetThingTypeRegistry(ThingTypeRegistry thingTypeRegistry) {
	     this.thingTypeRegistry = null;
	}
	
}


