package name.zhangmin.gw.io.rest.resources.beans;

import java.util.List;

/**
 * This is a java bean that is used with JAX-RS to serialize things to JSON.
 * 
 * @author ZhangMin.name
 *
 */
public class ThingTypeBean {

    public List<ChannelDefinitionBean> channels;
    public List<ConfigDescriptionParameterBean> configParameters;
    public String description;
    public String label;

    public String UID;

    public ThingTypeBean() {
    }

    public ThingTypeBean(String UID, String label, String description,
            List<ConfigDescriptionParameterBean> configParameters, List<ChannelDefinitionBean> channels) {
        this.UID = UID;
        this.label = label;
        this.description = description;
        this.configParameters = configParameters;
        this.channels = channels;
    }

}
