/**
 * 
 */
package name.zhangmin.gw.core.lib.type.impl;

import name.zhangmin.gw.core.lib.type.State;

/**
 * @author ZhangMin.name
 *
 */
public class IntegerType implements State{
	Integer value;
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return value;
	}
	@Override
	public String format(String pattern) {
		return value.toString();
	}

}
