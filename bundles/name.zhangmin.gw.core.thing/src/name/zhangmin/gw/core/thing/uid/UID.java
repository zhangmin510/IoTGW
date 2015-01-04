/**
 * 
 */
package name.zhangmin.gw.core.thing.uid;

import java.util.Arrays;

import com.google.common.base.Joiner;

/**
 * This class is base class for unique idntifiers within the IoTGW.
 * A UID must always start with a binding ID.
 * @author ZhangMin.name
 *
 */
public abstract class UID {
	private static final String SEPARATOR = ":";
	private final String[] segments;
	
	/**
	 * Parses a  UID for a given string. The UID must be in the 
	 * format 'bindingID:segment:segment:...'.
	 * 
	 * @param uid uid in form a string (must not null)
	 */
	public UID(String uid) {
		this(splitToSegments(uid));
	}
	public UID(String... segments) {
		if (segments == null)
			throw new IllegalArgumentException("Given segments must not be null");
		int numberOfSegments = getMinimalNumberOfSegments();
		if (segments.length < numberOfSegments)
			throw new IllegalArgumentException("UID must have at least " + numberOfSegments);
		this.segments = segments;
	}
	
	/**
	 * Specifies how many segments the UID has to have at least.
	 * 
	 * @return
	 */
	protected abstract int getMinimalNumberOfSegments();
	
	protected String[] getSegments() {
		return this.segments;
	}
	
	protected String getSegment(int segment) {
		return this.segments[segment];
	}
	
	public String getBindingId() {
		return segments[0];
	}
	
	@Override
	public String toString() {
		return Joiner.on(this.SEPARATOR).join(segments);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(segments);
		return result;
	}
	
	@Override
	public boolean equals(Object that) {
		if (this == that) return true;
		if (that == null) return false;
		if (this.getClass() != that.getClass()) return false;
		UID other = (UID) that;
		if (!Arrays.equals(segments, other.segments)) return false;
		return true;
	}
	
	private static String[] splitToSegments(String uid) {
		if (uid == null) 
			throw new IllegalArgumentException("Given uid muste not be null");
		return uid.split(SEPARATOR);
	}
	
}
