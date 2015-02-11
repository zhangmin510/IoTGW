package name.zhangmin.gw.io.rest.core.thing.beans;

import java.util.Set;

/**
 * This is a java bean that is used to serialize channel definitions.
 * 
 * @author ZhangMin.name
 */
public class ChannelDefinitionBean {

    public String description;
    public String id;
    public String label;
    public Set<String> tags;

    public ChannelDefinitionBean() {

    }

    public ChannelDefinitionBean(String id, String label, String description, Set<String> tags) {
        this.description = description;
        this.label = label;
        this.id = id;
        this.tags = tags;
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

    public Set<String> getTags() {
        return tags;
    }

}
