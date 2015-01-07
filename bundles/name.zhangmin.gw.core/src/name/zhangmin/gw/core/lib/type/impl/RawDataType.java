package name.zhangmin.gw.core.lib.type.impl;

import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;
import name.zhangmin.gw.core.lib.type.State;



/**
 * This type can be used for all binary data such as images, documents, sounds etc.
 * Note that it is NOT adequate for any kind of streams, but only for fixed-size data.
 * 
 * @author ZhangMin.name
 *
 */
public class RawDataType implements State {
	
	protected byte[] bytes;
	
	
	public RawDataType() {
		this(new byte[0]);
	}
	
	public RawDataType(byte[] bytes) {
		this.bytes = bytes;
	}
		
	public byte[] getBytes() {
		return bytes;
	}
		
	public static RawDataType valueOf(String value) {
		return new RawDataType(DatatypeConverter.parseBase64Binary(value));
	}
	
	@Override
	public String toString() {
		return DatatypeConverter.printBase64Binary(bytes);
	}

	@Override
	public String format(String pattern) {
		return toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RawDataType other = (RawDataType) obj;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		return true;
	}

}
