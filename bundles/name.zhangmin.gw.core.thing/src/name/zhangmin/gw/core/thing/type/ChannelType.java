/**
 * 
 */
package name.zhangmin.gw.core.thing.type;

/**
 * This class descrbes a concrete type of a {@link Channel}.
 * <p>
 * This description is used as template definition for the creation
 * of the according concrete {@link Channel} object.
 * <p>
 * <b>Hint:</b>This class is immutable.
 * 
 * @author ZhangMin.name
 *
 */
public class ChannelType extends AbstractDescriptionType {

	private String appType;
	
    /**
     * Creates a new instance of this class with the specified parameters.
     * 
     * @param uid the unique identifier which identifies this Channel type within
     *     the overall system (must neither be null, nor empty)
     * 
     * @param appType the app type of this Channel type, e.g. 
     *     (must neither be null nor empty)
     * 
     * @param label the human readable label for the according type
     *     (must neither be null nor empty)
     * 
     * @param description the human readable description for the according type
     *     (must neither be null nor empty)
     *  
     * @param configDescriptionURI the link to the concrete ConfigDescription (could be null)
     * 
     * @throws IllegalArgumentException if the UID or the item type is null or empty,
     *     or the the meta information is null
     */
	public ChannelType(ChannelTypeUID uid, String appType, String label, String description,
			String configDescriptionURI) throws IllegalArgumentException{
		super(uid, label, description, configDescriptionURI);
		if ((appType == null) || (appType.isEmpty()))
			throw new IllegalArgumentException("The app type must neither be null nor empty");
		this.appType = appType;
	}

    /**
     * Returns the item type of this {@link ChannelType}, e.g. {@code ColorItem}.
     * 
     * @return the item type of this Channel type, e.g. {@code ColorItem} (neither null nor empty)
     */
    public String getItemType() {
        return this.appType;
    }

    @Override
    public ChannelTypeUID getUID() {
        return (ChannelTypeUID) super.getUID();
    }
}
