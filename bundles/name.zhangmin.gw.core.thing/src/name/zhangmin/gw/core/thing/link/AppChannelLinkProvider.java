
package name.zhangmin.gw.core.thing.link;

import java.util.Collection;

/**
 * The {@link AppChannelLinkProvider} is responsible for providing item channel
 * links.
 * 
 * @author ZhangMin.name
 * 
 */
public interface AppChannelLinkProvider {

    /**
     * Provides a collection of item channel links.
     * 
     * @return the item channel links provided by the
     *         {@link AppChannelLinkProvider}
     */
    Collection<AppChannelLink> getAppChannelLinks();

    /**
     * Adds a {@link AppChannelLinksChangeListener} which is notified if there
     * are changes concerning the item channel links provides by the
     * {@link AppChannelLinkProvider}.
     * 
     * @param listener
     *            The listener to be added
     */
    void addAppChannelLinksChangeListener(AppChannelLinksChangeListener listener);

    /**
     * Removes a {@link AppChannelLinksChangeListener} which is notified if
     * there are changes concerning the item channel links provides by the
     * {@link AppChannelLinkProvider}.
     * 
     * @param listener
     *            The listener to be removed.
     */
    void removeAppChannelLinksChangeListener(AppChannelLinksChangeListener listener);
}
