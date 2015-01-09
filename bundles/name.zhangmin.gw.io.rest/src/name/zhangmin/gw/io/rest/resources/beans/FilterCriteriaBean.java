package name.zhangmin.gw.io.rest.resources.beans;

/**
 * This is a java bean that is used with JAX-RS to serialize filter criteria of a
 * parameter to JSON.
 * 
 * @author ZhangMin.name
 *
 */
public class FilterCriteriaBean {

    public String value;
    public String name;

    public FilterCriteriaBean() {
    }

    public FilterCriteriaBean(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
