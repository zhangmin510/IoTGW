package name.zhangmin.gw.io.rest.resources.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a java bean that is used with JAX-RS to serialize things to JSON.
 * 
 * @author ZhangMin.name
 *
 */
@XmlRootElement(name = "thing-type")
public class ThingTypeBean {

	public String UID;
	public String manufacturer;
    public String description;
    public String label;
    public List<ChannelDefinitionBean> channelDefinition;
    

    public ThingTypeBean() {
    }

    public ThingTypeBean(String UID, String manufacturer, String label, String description, List<ChannelDefinitionBean> channels) {
        this.UID = UID;
        this.manufacturer = manufacturer;
        this.label = label;
        this.description = description;
        this.channelDefinition = channels;
    }

}
