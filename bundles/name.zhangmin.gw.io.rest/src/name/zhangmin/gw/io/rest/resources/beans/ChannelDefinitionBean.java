package name.zhangmin.gw.io.rest.resources.beans;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a java bean that is used to serialize channel definitions.
 * 
 * @author ZhangMin.name
 */
@XmlRootElement
public class ChannelDefinitionBean {
	
    public String id;
    public String label;
    public String description;
    
    public ChannelDefinitionBean() {

    }

    public ChannelDefinitionBean(String id, String label, String description) {
        this.description = description;
        this.label = label;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
