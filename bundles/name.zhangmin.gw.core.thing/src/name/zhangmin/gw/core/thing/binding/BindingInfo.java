/**
 * 
 */
package name.zhangmin.gw.core.thing.binding;

/**
 * The {@link BindingInfo} is a service interface each <i>Binding</i> has to
 * implement to provide general information. Each <i>Binding</i> has to register
 * its implementation as a serviceat the <i>OSGi</i> service registry.
 * 
 * @author ZhangMin.name
 *
 */
public interface BindingInfo {

	/**
	 * Returns an identifier for the binding
	 * 
	 * @return an indentifier for the binding (must neither be null or empty)
	 */
	String getId();
	
	/**
	 * Returns a human readable name for the binding
	 * 
	 * @return a human readable name for the binding (must neither be null or empty)
	 */
	String getName();
	
	 /**
     * Returns a human readable description for the binding
     * 
     * @return a human readable description for the binding (must neither be null nor empty)
     */
	String getDescription();
	
	 /**
     * Returns the author of the binding.
     * 
     * @return the author of the binding (must neither be null, nor empty)
     */
	String getAuthor();
	
	/**
     * Returns {@code true} if a link to a concrete {@link ConfigDescription} exists,
     * otherwise {@code false}.
     * 
     * @return true if a link to a concrete ConfigDescription exists, otherwise false
     */
	boolean hasConfigDescriptionURI();
	
	/**
     * Returns the link to a concrete {@link ConfigDescription}.
     * 
     * @return the link to a concrete ConfigDescription (could be null)
     */
	String getConfigurationURI();
}
