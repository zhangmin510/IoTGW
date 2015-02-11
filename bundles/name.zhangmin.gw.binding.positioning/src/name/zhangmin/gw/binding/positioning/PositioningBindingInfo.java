/**
 * 
 */
package name.zhangmin.gw.binding.positioning;

import name.zhangmin.gw.core.thing.binding.BindingInfo;

/**
 * @author ZhangMin.name
 *
 */
public class PositioningBindingInfo implements BindingInfo{

	public final static String BINDING_ID = "positioning";
	@Override
	public String getId() {
		return BINDING_ID;
	}

	@Override
	public String getName() {
		return "Positioning Thing";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getAuthor() {
		return "ZhangMin.name";
	}

	@Override
	public boolean hasConfigDescriptionURI() {
		return false;
	}

	@Override
	public String getConfigurationURI() {
		return "";
	}

}
