/**
 * 
 */
package name.zhangmin.gw.core.lib.type.impl;

import name.zhangmin.gw.core.lib.type.State;

/**
 * @author ZhangMin.name
 *
 */
public class StateImpl implements State{
	private String state;
	
	@Override
	public String format(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return state;
	}

}
