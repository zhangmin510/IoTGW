/**
 * 
 */
package name.zhangmin.gw.core.type;

/**
 * This enum defining the event type.
 * @author ZhangMin.name
 *
 */
public enum EventType {
	COMMAND("command"),
	DATA("data");
	
	private String name;
	
	private EventType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}