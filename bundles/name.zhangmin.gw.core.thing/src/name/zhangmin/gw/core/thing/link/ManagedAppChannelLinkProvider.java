/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package name.zhangmin.gw.core.thing.link;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import name.zhangmin.gw.core.storage.Storage;
import name.zhangmin.gw.core.storage.StorageSelector;
import name.zhangmin.gw.core.storage.StorageSelector.StorageSelectionListener;
import name.zhangmin.gw.core.storage.StorageService;
import name.zhangmin.gw.core.thing.ThingActivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * {@link ManagedAppChannelLinkProvider} is responsible for managed
 * {@link AppChannelLink}s at runtime.
 * 
 * @author Dennis Nobel - Initial contribution
 * 
 */
public class ManagedAppChannelLinkProvider implements AppChannelLinkProvider,
        StorageSelectionListener<AppChannelLink> {

    private final static Logger logger = LoggerFactory
            .getLogger(ManagedAppChannelLinkProvider.class);

    private Collection<AppChannelLinksChangeListener> itemChannelLinksChangeListeners = new CopyOnWriteArrayList<>();
    private Storage<AppChannelLink> storage;
    private StorageSelector<AppChannelLink> storageSelector;

    public ManagedAppChannelLinkProvider() {
        this.storageSelector = new StorageSelector<>(ThingActivator.getContext(),
                AppChannelLink.class.getName(), this);
    }

    /**
     * Adds an {@link AppChannelLink}.
     * 
     * @param itemChannelLink
     *            item channel link
     */
    public void addItemChannelLink(AppChannelLink appChannelLink) {
        logger.info("Adding item channel link to managed item channel link provider '{}'.",
                appChannelLink.toString());
        AppChannelLink oldAppChannelLink = storage.put(appChannelLink.getID(), appChannelLink);
        if (oldAppChannelLink != null) {
            notifyAppChannelLinksChangeListenerAboutRemovedItemChannelLink(oldAppChannelLink);
        }
        notifyAppChannelLinksChangeListenerAboutAddedItemChannelLink(appChannelLink);
    }

    @Override
    public void addAppChannelLinksChangeListener(AppChannelLinksChangeListener listener) {
        itemChannelLinksChangeListeners.add(listener);
    }

    /**
     * Returns all managed {@link AppChannelLink}s.
     * 
     * @return all managed item channel links
     */
    @Override
    public Collection<AppChannelLink> getAppChannelLinks() {
        return storage.getValues();
    }

    /**
     * Removes an {@link AppChannelLink}.
     * 
     * @param appChannelLink
     *            item channel link
     * @return the removed item channel link or null if no link was removed
     */
    public AppChannelLink removeAppChannelLink(AppChannelLink appChannelLink) {
        logger.debug("Removing itemChannelLink from managed itemChannelLink provider '{}'.",
                appChannelLink.toString());
        AppChannelLink removedAppChannelLink = storage.remove(appChannelLink.getID());
        if (removedAppChannelLink != null) {
            notifyAppChannelLinksChangeListenerAboutRemovedItemChannelLink(removedAppChannelLink);
        }
        return removedAppChannelLink;
    }

    @Override
    public void removeAppChannelLinksChangeListener(AppChannelLinksChangeListener listener) {
        itemChannelLinksChangeListeners.remove(listener);
    }

    @Override
    public void storageSelected(Storage<AppChannelLink> storage) {
        this.storage = storage;
    }

    private void notifyAppChannelLinksChangeListenerAboutAddedItemChannelLink(
            AppChannelLink itemChannelLink) {
        for (AppChannelLinksChangeListener appChannelLinksChangeListener : this.itemChannelLinksChangeListeners) {
            appChannelLinksChangeListener.appChannelLinkAdded(this, itemChannelLink);
        }
    }

    private void notifyAppChannelLinksChangeListenerAboutRemovedItemChannelLink(
            AppChannelLink itemChannelLink) {
        for (AppChannelLinksChangeListener appChannelLinksChangeListener : this.itemChannelLinksChangeListeners) {
        	appChannelLinksChangeListener.appChannelLinkRemoved(this, itemChannelLink);
        }
    }

    protected void addStorageService(StorageService storageService) {
        this.storageSelector.addStorageService(storageService);
    }

    protected void removeStorageService(StorageService storageService) {
        this.storageSelector.removeStorageService(storageService);
    }

}
