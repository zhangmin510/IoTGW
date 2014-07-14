/**
 * 
 */
package name.zhangmin.gw.core.event;

/**
 * The {@link EventContants } interface defines the constants used by the <i>IoTGW</i>
 * event bus. Events can be received and sent by using the {@link EventPublisher} service.
 * 
 * @see EventPublisher
 * @see EventSubscriber
 * 
 * @author ZhangMin.name
 *
 */
public interface EventConstants {
	/**
	 * This constant defining the topic prefix for events associated with <i>IoTGW</i>.
	 * A topic is the name under which events are sent and under which listeners can
	 * subscribe to(see Event Admin Service).
	 * <p>
	 * Example: {@code gw/command/positioning}
	 * 
	 * @see #TOPIC_SEPERATOR
	 */
	String TOPIC_PREFIX = "iotgw";
	/**
	 * This constant defing the seperator for sub-topics.Each event of <i>IoTGW</i> is 
	 * sent under the topic prefix defined in {@link #TOPIC_PREFIX} and under the specific
	 * topic name.
	 * 
	 */
	String TOPIC_SEPERATOR = "/";
}
