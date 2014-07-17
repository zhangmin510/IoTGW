/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package name.zhangmin.gw.core.thing.link;

import name.zhangmin.gw.core.thing.ChannelUID;



/**
 * {@link AppChannelLink} defines a link between an {@link app} and a
 * {@link Channel}.
 * 
 * @author Dennis Nobel - Initial contribution
 */
public class AppChannelLink {

    private final String appName;

    private final ChannelUID channelUID;

    public AppChannelLink(String appName, ChannelUID channelUID) {
        this.appName = appName;
        this.channelUID = channelUID;
    }

    public String getAppName() {
        return appName;
    }

    public ChannelUID getChannelUID() {
        return channelUID;
    }

    public String getID() {
        return appName + " -> " + getChannelUID().toString();
    }

    @Override
    public String toString() {
        return getID();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
        	return false;
        }
        if (!(obj instanceof AppChannelLink)) {
        	return false;
        }
        AppChannelLink other = (AppChannelLink) obj;
        if (!this.appName.equals(other.appName)) {
        	return false;
        }
        if (!this.channelUID.equals(other.channelUID)) {
        	return false;
        }

        return true;
    }
    
    @Override
    public int hashCode() {
        return (int)this.appName.hashCode() *
                this.channelUID.hashCode();
    }

}
