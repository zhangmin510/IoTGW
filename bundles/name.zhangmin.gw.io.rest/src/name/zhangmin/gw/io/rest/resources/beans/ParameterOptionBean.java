package name.zhangmin.gw.io.rest.resources.beans;

/**
 * This is a java bean that is used with JAX-RS to serialize options of a
 * parameter to JSON.
 * 
 * @author ZhangMin.name
 *
 */
public class ParameterOptionBean {

    public String label;
    public String value;

    public ParameterOptionBean() {
    }

    public ParameterOptionBean(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
