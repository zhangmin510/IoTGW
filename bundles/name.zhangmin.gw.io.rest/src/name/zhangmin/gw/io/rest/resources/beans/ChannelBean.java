package name.zhangmin.gw.io.rest.resources.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a java bean that is used with JAX-RS to serialize channels to JSON.
 * 
 * @author ZhangMin.name
 *
 */
@XmlRootElement(name = "channel")
public class ChannelBean {

    public String boundApp;

    public String id;

    public String appType;

    public ChannelBean() {
    }

    public ChannelBean(String id, String itemType, String boundItem) {
        this.appType = itemType;
        this.id = id;
        this.boundApp = boundItem;
    }

}
