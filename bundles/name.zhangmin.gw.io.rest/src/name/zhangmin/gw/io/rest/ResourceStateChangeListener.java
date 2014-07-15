/**
 * 
 */
package name.zhangmin.gw.io.rest;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import name.zhangmin.gw.core.apps.App;

/**
 * This is an abstract super class for listeners that are used to determine, when new events
 * are available on the server side.
 * 
 * @author ZhangMin.name
 *
 */
abstract public class ResourceStateChangeListener {
	final static ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
	
	public ResourceStateChangeListener() { }
	
	public static ConcurrentMap<String, Object> getMap() {
		return map;
	}
	
	/**
	 * Returns a set of all apps that should be observed for this request.
	 * A data change of any of those app will resume the suspended request.
	 * @param pathInfo the pathInfo object from the http request
	 * @return a set of app name
	 */
	abstract protected Set<String> getRelevantAppNames(String pathInfo);
	/**
	 * Determines the response content for an HTTP request.
	 * This method has to do all the HTTP header evaluation itself that
	 * normally done through Jersey annotations.
	 * @param request the HttpServletRequest
	 * @return the response content
	 */
	abstract protected Object getResponseObject(final HttpServletRequest request);
	/**
	 * Determines the response content for a single item.
	 * This method has to do all the HTTP header evaluation itself that is normally
	 * done through Jersey annotations (if anybody knows a way to avoid this, let
	 * me know!)
	 * 
	 * @param item the app object
	 * @param request the HttpServletRequest
	 * @return the response content
	 */
	abstract protected Object getSingleResponseObject(App app, final HttpServletRequest request);
	
}
