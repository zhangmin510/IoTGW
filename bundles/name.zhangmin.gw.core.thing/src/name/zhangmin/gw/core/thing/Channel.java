/**
 * 
 */
package name.zhangmin.gw.core.thing;

import name.zhangmin.gw.config.Configuration;

/**
 * This class respresens a Channel which ia a part of a {@link Thing}.
 * It represents a functionality of the Thing it belongs to. Therefore,
 * {@link App}s can be bound to a channel. The channle only accepts a 
 * specific app type which is specified by {@link Channel#getAcceptedAppType()}
 * methods.
 * @author ZhangMin.name
 *
 */
public class Channel {
	private String acceptedAppType;
	private ChannelUID uid;
	private Configuration configuration;
	public Channel(ChannelUID uid, String acceptedAppType) {
		this.uid = uid;
		this.acceptedAppType = acceptedAppType;
	}
	public Channel(ChannelUID uid, String acceptedAppType, Configuration configuration) {
		this.uid = uid;
		this.acceptedAppType = acceptedAppType;
		this.configuration = configuration;
	}
	public String getAcceptedAppType() {
		return this.acceptedAppType;
	}
	public ChannelUID getUID() {
		return this.uid;
	}
	public Configuration getConfiguratin() {
		return this.configuration;
	}
}
