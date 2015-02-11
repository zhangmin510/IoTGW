/**
 * 
 */
package name.zhangmin.gw.io.rest.resources.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ZhangMin.name
 *
 */
@XmlRootElement(name = "app-channle-link")
public class AppChannelLinkBean {
	private String channelUID;
	private String appName;
	
	public AppChannelLinkBean() {
		
	}
	
	public AppChannelLinkBean(String appName, String channelUID) {
		this.appName = appName;
		this.channelUID = channelUID;
	}

	public String getChannelUID() {
		return channelUID;
	}

	public void setChannelUID(String channelUID) {
		this.channelUID = channelUID;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	

}
