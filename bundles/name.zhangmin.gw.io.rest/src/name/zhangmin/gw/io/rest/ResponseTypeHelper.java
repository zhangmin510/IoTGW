/**
 * 
 */
package name.zhangmin.gw.io.rest;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
/**
 * @author ZhangMin.name
 *
 */
public class ResponseTypeHelper {
	public String getResponseType(HttpServletRequest request) {
		List<MediaType> mediaTypes = getAcceptedMediaTypes(request);
		String type = getQueryParam(request, "type");
		String responseType = MediaTypeHelper.getResponseMediaType(mediaTypes, type);
		return responseType;
	}
	protected List<MediaType> getAcceptedMediaTypes(HttpServletRequest request) {
		String[] acceptedMediaTypes = request.getHeader(HttpHeaders.ACCEPT).split(",");
		List<MediaType> mediaTypes = new ArrayList<MediaType>(acceptedMediaTypes.length);
		for (String type : acceptedMediaTypes) {
			MediaType mediaType = MediaType.valueOf(type.trim());
			if (mediaType != null) mediaTypes.add(mediaType);
		}
		return mediaTypes;
	}
	
	public String getQueryParam(HttpServletRequest request, String paramName) {
		if (request.getQueryString() == null) return null;
		String[] pairs = request.getQueryString().split("&");
		for (String pair : pairs) {
			String[] keyValue = pair.split("=");
			if (keyValue[0].trim().equals(paramName)) return keyValue[1].trim();
		}
		return null;
	}
	
	protected String getQueryParam(HttpServletRequest request, String paramName, String defaultValue) {
		String value = getQueryParam(request, paramName);
		return value != null ? value : defaultValue;
	}
}
