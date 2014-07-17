/**
 * 
 */
package name.zhangmin.gw.config;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ZhangMin.name
 *
 */
public class Configuration {

    private Map<String, Object> properties = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(Configuration.class);

    public <T> T as(Class<T> configurationClass) {
        T configuration = null;
        try {
            configuration = configurationClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.error("Could not create configuration instance: " + ex.getMessage(), ex);
            return null;
        }
        Field[] declaredFields = configurationClass.getDeclaredFields();
        for (Field field : declaredFields) {
            Object value = this.get(field.getName());
            if (value != null) {
                try {
                    field.set(configuration, value);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    logger.warn("Could not set field value for field '" + field.getName() + "': "
                            + ex.getMessage(), ex);
                }
            }
        }
        return configuration;
    }

    public Object get(Object key) {
        return properties.get(key);
    }

    public Object put(String key, Object value) {
        return properties.put(key, value);
    }

    public Object remove(Object key) {
        return properties.remove(key);
    }

    public Set<String> keySet() {
        return properties.keySet();
    }

    public Collection<Object> values() {
        return properties.values();
    }

    public boolean isProperlyConfigured() {
        return true;
    }
    
    @Override
    public int hashCode() {
    	return properties.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj)
    		return true;
    	if (!(obj instanceof Configuration))
    		return false;
    	return this.hashCode() == obj.hashCode();
    }
}
