/**
 * 
 */
package name.zhangmin.gw.core.thing.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import name.zhangmin.gw.core.thing.uid.ThingTypeUID;


/**
 * This class describes a concrete type of a {@link Thing}.
 * <p>
 * This description is used as template definition for the creation
 * of the according concrete {@link Thing} object.
 * <p>
 * <b>Hint:</b> This class is immutable.
 * @author ZhangMin.name
 *
 */
public class ThingType extends AbstractDescriptionType{

	private final List<ChannelDefinition> channelDefinitions;
	private final String manufacturer;

    /**
     * @see ThingType#ThingType(String, List, String, String, String,
     *      List, String)
     */
    public ThingType(String bindingId, String thingTypeId, String label, String description,
            String manufacturer)
            throws IllegalArgumentException {
        this(new ThingTypeUID(bindingId, thingTypeId), label, description, manufacturer,null, null);
    }

    /**
     * Creates a new instance of this class with the specified parameters.
     * 
     * @param uid
     *            the unique identifier which identifies this Thing type within
     *            the overall system (must neither be null, nor empty)
     * 
     * @param label the human readable label for the according type
     *     (must neither be null nor empty)
     * 
     * @param description the human readable description for the according type
     *     (must neither be null nor empty)
     * 
     * @param label the human readable label for the according type
     *     (must neither be null nor empty)
     * 
     * @param description the human readable description for the according type
     *     (must neither be null nor empty)
     * 
     * @param channelDefinitions
     *            the channels this Thing type provides (could be null or empty)
     * 
     * @param configDescriptionURI
     *            the link to the concrete ConfigDescription (could be null)
     * 
     * @throws IllegalArgumentException
     *             if the UID is null or empty, or the the meta information is
     *             null
     */
    public ThingType(ThingTypeUID uid, String label, String description, String manufacturer,
            List<ChannelDefinition> channelDefinitions, String configDescriptionURI)
            throws IllegalArgumentException {

        super(uid, label, description, configDescriptionURI);

        this.manufacturer = manufacturer;


        if (channelDefinitions != null) {
            this.channelDefinitions = Collections.unmodifiableList(channelDefinitions);
        } else {
            this.channelDefinitions = Collections
                    .unmodifiableList(new ArrayList<ChannelDefinition>(0));
        }
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ThingType other = (ThingType) obj;

        return this.getUID().equals(other.getUID());
    }

    /**
     * Returns the binding id
     * 
     * @return binding id (could not be null)
     */
    public String getBindingId() {
        return this.getUID().getBindingId();
    }

    /**
     * Returns the channels this {@link ThingType} provides.
     * <p>
     * The returned list is immutable.
     * 
     * @return the channels this Thing type provides (could not be null)
     */
    public List<ChannelDefinition> getChannelDefinitions() {
        return this.channelDefinitions;
    }

    /**
     * Returns the id.
     * 
     * @return id (could not be null)
     */
    public ThingTypeUID getUID() {
        return (ThingTypeUID) super.getUID();
    }

    /**
     * Returns the human readable name of the manufacturer.
     * 
     * @return the human readable name of the manufacturer (could be null or
     *         empty)
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    @Override
    public int hashCode() {
       return getUID().hashCode();
    }

    @Override
    public String toString() {
        return getUID().toString();
    }
}
