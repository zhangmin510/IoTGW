/**
 * 
 */
package name.zhangmin.gw.core.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.LoggerFactory;

import name.zhangmin.gw.core.event.EventPublisher;
import name.zhangmin.gw.core.type.Command;
import name.zhangmin.gw.core.type.Data;
import name.zhangmin.gw.core.type.State;

/**
 * This abstract base class for all apps. It provides all relevant logic
 * for the infranstructure, such as publishing updates to the event bus
 * or notifying listeners.
 * 
 * @author ZhangMin.name
 *
 */
public abstract class GenericApp implements App {

	protected EventPublisher eventPublisher;
	protected Set<StateChangeListener> listeners = 
			new CopyOnWriteArraySet<StateChangeListener>(Collections.newSetFromMap(new WeakHashMap<StateChangeListener, Boolean>()));
	final protected String name;
	final protected String type;
	protected State state = null;
	
	public GenericApp(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	
	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.apps.App#getState()
	 */
	@Override
	public State getState() {
		return this.state;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.apps.App#getStateAs(java.lang.Class)
	 */
	@Override
	public State getStateAs(Class<? extends State> typeClass) {
		if (typeClass != null && typeClass.isInstance(state))
			return state;
		else return null;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.apps.App#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see name.zhangmin.gw.core.apps.App#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	public void initialize() { }
	public void dispose() {
		this.eventPublisher = null;
	}

	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	protected void internalSend(Command command) {
		if (eventPublisher != null)
			eventPublisher.sendCommand(this.getName(), command);
	}
	public void setState(State state) {
		State oldState = this.state;
		this.state = state;
		notifyListeners(oldState, state);
	}
	private void notifyListeners(State oldState, State newState) {
		Set<StateChangeListener> clonedListeners = null;
		clonedListeners = new CopyOnWriteArraySet<StateChangeListener>(listeners);
		for (StateChangeListener listener : clonedListeners) {
			listener.stateUpdated(this, newState);
		}
		if (!oldState.equals(newState)) {
			for (StateChangeListener listener : clonedListeners) {
				listener.stateChanged(this, oldState, newState);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getName() + " (" + "Type=" + getClass().getSimpleName() +
				", " + "State=" + getState() + ")";
	}
	public void addStateChangeListener(StateChangeListener listener) {
		synchronized(listeners) {
			listeners.add(listener);
		}
	}
	public void removeStateChangeListener(StateChangeListener listener) {
		synchronized(listeners) {
			listeners.remove(listener);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		GenericApp other = (GenericApp) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
}
