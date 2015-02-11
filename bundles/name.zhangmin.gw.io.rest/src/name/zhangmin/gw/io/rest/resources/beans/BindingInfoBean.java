/**
 * 
 */
package name.zhangmin.gw.io.rest.resources.beans;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author ZhangMin.name
 *
 */
@XmlRootElement(name = "binding-info")
public class BindingInfoBean {
    public String author;
    public String description;
    public String id;
    public String name;
    public Set<ThingTypeBean> thingTypes;

    public BindingInfoBean() {
    }

    public BindingInfoBean(String id, String name, String author, String description, Set<ThingTypeBean> thingTypes) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.thingTypes = thingTypes;
    }
	

}
