
package name.zhangmin.gw.core.thing.link;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import name.zhangmin.gw.core.thing.ChannelUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;


/**
 * {@link AppChannelLinkRegistry} tracks all {@link AppChannelLinkProvider}s
 * and aggregates all {@link AppChannelLink}s.
 * 
 * 
 */
public class AppChannelLinkRegistry implements AppChannelLinksChangeListener {

    private final static Logger logger = LoggerFactory.getLogger(AppChannelLinkRegistry.class
            .getName());

    private Map<AppChannelLinkProvider, Collection<AppChannelLink>> appChannelLinkMap = new ConcurrentHashMap<>();

    /**
     * Returns the App name, which is bound to the given channel UID.
     * 
     * @param channelUID
     *            channel UID
     * @return App name or null if no App is bound to the given channel UID
     */
    public String getBoundApp(ChannelUID channelUID) {
        for (AppChannelLink appChannelLink : getAppChannelLinks()) {
            if (appChannelLink.getChannelUID().equals(channelUID)) {
                return appChannelLink.getAppName();
            }
        }
        return null;
    }

    /**
     * Returns all App channel links.
     * 
     * @return all App channel links
     */
    public List<AppChannelLink> getAppChannelLinks() {
        return Lists.newArrayList(Iterables.concat(appChannelLinkMap.values()));
    }

    /**
     * Returns if an App for a given App is linked to a channel for a given
     * channel UID.
     * 
     * @param AppName
     *            App name
     * @param channelUID
     *            channel UID
     * @return true if linked, false otherwise
     */
    public boolean isLinked(String AppName, ChannelUID channelUID) {

        for (AppChannelLink appChannelLink : getAppChannelLinks()) {
            if (appChannelLink.getChannelUID().equals(channelUID)
                    && appChannelLink.getAppName().equals(AppName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void appChannelLinkAdded(AppChannelLinkProvider provider,
            AppChannelLink appChannelLink) {
        Collection<AppChannelLink> AppChannelLinks = appChannelLinkMap.get(provider);
        if (AppChannelLinks != null) {
            AppChannelLinks.add(appChannelLink);
        }
    }

    @Override
    public void appChannelLinkRemoved(AppChannelLinkProvider provider,
            AppChannelLink appChannelLink) {
        Collection<AppChannelLink> appChannelLinks = appChannelLinkMap.get(provider);
        if (appChannelLinks != null) {
        	appChannelLinks.remove(appChannelLink);
        }
    }

	@Override
	public void appChannelLinkUpdated(AppChannelLinkProvider provider,
			AppChannelLink oldAppChannelLink,
			AppChannelLink newAppChannelLink) {
		appChannelLinkRemoved(provider, oldAppChannelLink);
		appChannelLinkAdded(provider, newAppChannelLink);
	}

    protected void setAppChannelLinkProvider(AppChannelLinkProvider AppChannelLinkProvider) {
        // only add this provider if it does not already exist
        if (!appChannelLinkMap.containsKey(AppChannelLinkProvider)) {
            Collection<AppChannelLink> AppChannelLinks = new CopyOnWriteArraySet<AppChannelLink>(
                    AppChannelLinkProvider.getAppChannelLinks());
            appChannelLinkMap.put(AppChannelLinkProvider, AppChannelLinks);
            AppChannelLinkProvider.addAppChannelLinksChangeListener(this);
            logger.debug("AppChannelLinkProvider '{}' has been added.", AppChannelLinkProvider
                    .getClass().getName());
        }
    }

    protected void removeAppChannelLinkProvider(AppChannelLinkProvider appChannelLinkProvider) {
        if (appChannelLinkMap.containsKey(appChannelLinkProvider)) {
            appChannelLinkMap.remove(appChannelLinkProvider);
            appChannelLinkProvider.removeAppChannelLinksChangeListener(this);
            logger.debug("AppChannelLinkProvider '{}' has been removed.", appChannelLinkProvider
                    .getClass().getName());
        }
    }

}
