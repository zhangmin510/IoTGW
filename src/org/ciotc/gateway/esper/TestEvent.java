/**
 *
 * TestEvent.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * PositioningGW
 *
 */
package org.ciotc.gateway.esper;

/**
 * @author ZhangMin.name
 *
 */
public class TestEvent {
	private final String name;
	private final int num;
	public TestEvent(String name,int num){
		this.name = name;
		this.num = num;
	}
	public String getName(){
		return this.name;
	}
	public int getNum(){
		return this.num;
	}
}
