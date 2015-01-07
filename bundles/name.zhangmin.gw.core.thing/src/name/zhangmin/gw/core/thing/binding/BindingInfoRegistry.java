/**
 * 
 */
package name.zhangmin.gw.core.thing.binding;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * {@link BindingInfoREgistry} tracks all (@link BindingInfo} OSGi service
 * and provides access to them.
 * 
 * @author ZhangMin.name
 *
 */
public class BindingInfoRegistry {
	private List<BindingInfo> bindingInfos = new CopyOnWriteArrayList<BindingInfo>();
	
	public void addBindingInfo(BindingInfo bindingInfo) {
		bindingInfos.add(bindingInfo);
	}
	
	public void removeBindingInfo(BindingInfo bindingInfo) {
		bindingInfos.remove(bindingInfo);
	}
	
	/**
	 * Returns all {@link BindingInfo} that are registered as OSGi services.
	 * @return all {@link BindingInfo} OSGi services
	 */
	public List<BindingInfo> getBindingInfos() {
		return Collections.unmodifiableList(bindingInfos);
	}
}
