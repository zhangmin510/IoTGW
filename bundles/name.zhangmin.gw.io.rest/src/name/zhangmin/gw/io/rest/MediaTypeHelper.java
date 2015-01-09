/**
 * 
 */
package name.zhangmin.gw.io.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

/**
 * This class contains static helper methods for dealing
 * with MediaTypes.
 * 
 * @author ZhangMin.name
 *
 */
public class MediaTypeHelper {
	public static final String APPLICATION_X_JAVASCRIPT = "application/x-javascript";
	
	/**
	 * This is a helper method to determine the respponse media type depending on a list
	 * of accepted types and an explicitely declared type parameter.
	 * Note that the explicit type parameter takes precedence over the accepted types.
	 * @param acceptedTypes accepted media types
	 * @param typeParam either "xml", "json", "jsonp", or null
	 * @return the media type to use for the response
	 */
	static public String getResponseMediaType(List<MediaType> acceptedTypes, String typeParam) {
		if ("xml".equals(typeParam)) {
			return MediaType.APPLICATION_XML;
		} else if ("json".equals(typeParam)) {
			return MediaType.APPLICATION_JSON;
		} else if ("jsonp".equals(typeParam)) {
			return APPLICATION_X_JAVASCRIPT;
		}
		
		for (MediaType type : acceptedTypes) {
			if (type.isCompatible(MediaType.APPLICATION_XML_TYPE)) {
				return MediaType.APPLICATION_XML;
			} else if (type.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
				return MediaType.APPLICATION_JSON;
			} else if (type.toString().equals(APPLICATION_X_JAVASCRIPT)) {
				return APPLICATION_X_JAVASCRIPT;
			}
		}
		
		return null;
	}
	
	
}
