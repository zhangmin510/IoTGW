/**
 * 
 */
package name.zhangmin.gw.core.thing.binding.builder;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.thing.Channel;
import name.zhangmin.gw.core.thing.ChannelUID;

/**
 * @author ZhangMin.name
 *
 */
public class ChannelBuilder {
    private ChannelUID channelUID;
    private String acceptedItemType;
    private Configuration configuration;

    private ChannelBuilder(ChannelUID channelUID, String acceptedItemType) {
        this.channelUID = channelUID;
        this.acceptedItemType = acceptedItemType;
    }

    /**
     * Creates a channel builder for the given channel UID and item type.
     * 
     * @param channelUID
     *            channel UID
     * @param acceptedItemType
     *            item type that is accepted by this channel
     * @return channe builder
     */
    public static ChannelBuilder create(ChannelUID channelUID, String acceptedItemType) {
        return new ChannelBuilder(channelUID, acceptedItemType);
    }

    /**
     * Appends a configuration to the channel to build.
     * 
     * @param configuration
     *            configuration
     * @return channel builder
     */
    public ChannelBuilder withConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * Builds and returns the channel.
     * 
     * @return channel
     */
    public Channel build() {
        return new Channel(channelUID, acceptedItemType, configuration);
    }
}
