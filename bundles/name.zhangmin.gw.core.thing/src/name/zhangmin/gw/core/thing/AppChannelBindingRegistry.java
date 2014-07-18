/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package name.zhangmin.gw.core.thing;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.osgi.service.component.ComponentContext;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * TODO: this implementation must be changed. Its PROTOTYPE.
 */
public class AppChannelBindingRegistry implements ThingRegistryChangeListener {

    private Multimap<String, ChannelUID> appChannelBindings = Multimaps.synchronizedMultimap(ArrayListMultimap
            .<String, ChannelUID> create());

    private ThingRegistry thingRegistry;

    public void bind(String appName, ChannelUID channelUID) {
        //String boundappName = getBoundapp(channel);
        // if (boundappName != null && !boundappName.equals(appName)) {
        // throw new
        // IllegalArgumentException("Channel is already bound to an app.");
        // }
        appChannelBindings.put(appName, channelUID);
    }

    public List<ChannelUID> getBoundChannels(String appName) {
        return Lists.newArrayList(appChannelBindings.get(appName));
    }

    public String getBoundApp(ChannelUID channelUID) {
        Collection<Entry<String, ChannelUID>> entries = appChannelBindings.entries();
        for (Entry<String, ChannelUID> entry : entries) {
            if (entry.getValue().equals(channelUID)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isBound(String appName, ChannelUID channelUID) {
        return appChannelBindings.containsEntry(appName, channelUID);
    }

	@Override
	public void thingAdded(Thing thing) {
		// nothing to do
	}

	@Override
	public void thingRemoved(Thing thing) {
        List<Channel> channels = thing.getChannels();
        if (channels != null) {
        	for (Channel channel : channels) {
        		unbind(channel.getUID());
        	}       	
        }
	}

    public void unbind(ChannelUID channelUID) {
        String boundappName = getBoundApp(channelUID);
        if (boundappName != null) {
            appChannelBindings.remove(boundappName, channelUID);
        }
    }

    protected void activate(ComponentContext componentContext) {
        thingRegistry.addThingRegistryChangeListener(this);
    }

    protected void deactivate(ComponentContext componentContext) {
        thingRegistry.removeThingRegistryChangeListener(this);
    }

    protected void setThingRegistry(ThingRegistry thingRegistry) {
        this.thingRegistry = thingRegistry;
    }

    protected void unsetThingRegistry(ThingRegistry thingRegistry) {
        this.thingRegistry = null;
    }


}
