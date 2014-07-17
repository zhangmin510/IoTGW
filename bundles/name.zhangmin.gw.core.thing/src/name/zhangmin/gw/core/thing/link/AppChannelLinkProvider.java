/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package name.zhangmin.gw.core.thing.link;

import java.util.Collection;

/**
 * The {@link AppChannelLinkProvider} is responsible for providing item channel
 * links.
 * 
 * @author Dennis Nobel - Initial contribution
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
