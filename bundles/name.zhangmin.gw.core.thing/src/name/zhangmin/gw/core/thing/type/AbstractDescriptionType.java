/**
 * 
 */
package name.zhangmin.gw.core.thing.type;


import name.zhangmin.gw.core.thing.UID;

/**
 * This class is the base class for a
 * {@link ThingType} or {@link ChannelType}.
 * This class contains only properties and method accessing them.
 * <p>
 * <b>Hint:</b> This class is immutable
 * 
 * @author ZhangMin.name
 *
 */
public abstract class AbstractDescriptionType {

	private UID uid;
	private String configDescriptionURI;
	private String label;
	private String description;
	
	public AbstractDescriptionType(UID uid, String label, String description,
			String configDescriptionURI) throws IllegalArgumentException {
		if (uid == null)
			throw new IllegalArgumentException("The UID must not be null");
		
        if ((label == null) || (label.isEmpty())) {
            throw new IllegalArgumentException("The label must neither be null nor empty!");
        }

        if ((description == null) || (description.isEmpty())) {
            throw new IllegalArgumentException("The description must neither be null nor empty!");
        }

        this.uid = uid;
        this.label = label;
        this.description = description;
        this.configDescriptionURI = configDescriptionURI;
	}

    /**
     * Returns the unique identifier which identifies the according type within
     * the overall system.
     * 
     * @return the unique identifier which identifies the according type within
     *     the overall system (neither null, nor empty)
     */
    public UID getUID() {
        return this.uid;
    }

    /**
     * Returns {@code true} if a link to a concrete {@link ConfigDescription} exists,
     * otherwise {@code false}. 
     * 
     * @return true if a link to a concrete ConfigDescription exists, otherwise false
     */
    public boolean hasConfigDescriptionURI() {
        return (this.configDescriptionURI != null);
    }

    /**
     * Returns the link to a concrete {@link ConfigDescription}.
     * 
     * @return the link to a concrete ConfigDescription (could be null)
     */
    public String getConfigDescriptionURI() {
        return this.configDescriptionURI;
    }
    

    /**
     * Returns the human readable label for the according type.
     * 
     * @return the human readable label for the according type (neither null, nor empty)
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Returns the human readable description for the according type.
     * 
     * @return the human readable description for the according type (neither null, nor empty)
     */
    public String getDescription() {
        return this.description;
    }

	
}
