/**
 * 
 */
package name.zhangmin.gw.binding.positioning.app;

import java.util.ArrayList;
import java.util.List;

import name.zhangmin.gw.core.apps.GenericApp;
import name.zhangmin.gw.core.lib.type.Command;
import name.zhangmin.gw.core.lib.type.State;

/**
 * @author ZhangMin.name
 *
 */
public class PositioningApp extends GenericApp{
	
	public PositioningApp(String type, String name) {
		super(type, name);
	}

	@Override
	public List<Class<? extends State>> getAcceptedStateTypes() {
		List<Class<? extends State>> ret = new ArrayList<>();
		ret.add(State.class);
		return ret;
	}

	@Override
	public List<Class<? extends Command>> getAcceptedCommandTypes() {
		List<Class<? extends Command>> ret = new ArrayList<>();
		ret.add(Command.class);
		return ret;
	}

}
