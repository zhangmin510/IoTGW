package name.zhangmin.gw.config;


/**
 * {@link ConfigDescriptionListener} can be implemented as a listener for added
 * and removed {@link ConfigDescription}s. It can be added to a
 * {@link ConfigDescriptionProvider}.
 * 
 * @see ConfigDescriptionProvider
 * @author ZhangMin.name
 * 
 */
public interface ConfigDescriptionListener {

    /**
     * This method is called, when a {@link ConfigDescription} is added.
     * 
     * @param configDescription
     *            {@link ConfigDescription}, which was added
     */
    void configDescriptionAdded(ConfigDescription configDescription);

    /**
     * This method is called, when a {@link ConfigDescription} is removed.
     * 
     * @param configDescription
     *            {@link ConfigDescription}, which was removed
     */
    void configDescriptionRemoved(ConfigDescription configDescription);

}
