package name.zhangmin.gw.io.rest.resources.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.thing.ThingStatus;



/**
 * This is a java bean that is used with JAX-RS to serialize things to JSON.
 * 
 * @author ZhangMin.name
 *
 */
public class ThingBean {

	public String UID;
    public Map<String, Object> configuration;
    public ThingStatus status;
    public List<ChannelBean> channels;

    public ThingBean() {
    }

    public ThingBean(String UID, ThingStatus status, List<ChannelBean> channels,
            Configuration configuration) {
        this.UID = UID;
        this.status = status;
        this.channels = channels;
        this.configuration = toMap(configuration);
    }

    private Map<String, Object> toMap(Configuration configuration) {

        if (configuration == null) {
            return null;
        }

        Map<String, Object> configurationMap = new HashMap<>(configuration.keySet().size());
        for (String key : configuration.keySet()) {
            configurationMap.put(key, configuration.get(key));
        }
        return configurationMap;
    }

}
