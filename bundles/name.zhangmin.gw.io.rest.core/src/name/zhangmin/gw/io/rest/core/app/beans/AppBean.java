package name.zhangmin.gw.io.rest.core.app.beans;

import java.util.Set;


/**
 * This is a java bean that is used with JAXB to serialize items to JSON.
 *  
 * @author ZhangMin.name
 *
 */
public class AppBean {

	public String type;
	public String name;	
	public String state;
	public String link;
	public Set<String> tags; 
	
	public AppBean() {}
		
}
