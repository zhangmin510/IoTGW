/**
 * 
 */
package name.zhangmin.gw.core.lib.type;

/**
 * This is parent interface of all states and commands type.
 * @author ZhangMin.name
 *
 */
public interface Type {
	/**
	 * Fomats the value of this type according to a pattern.
	 * @param pattern the given pattern
	 * @return the formated string
	 */
	public String format(String pattern);
}
