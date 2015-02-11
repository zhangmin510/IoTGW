/**
 * 
 */
package name.zhangmin.gw.io.rest.test
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.TEXT
import javax.ws.rs.client.*

/**
 * @author ZhangMin.name
 *
 */



 
def http = new HTTPBuilder('http://www.baidu.com')
 
def html = http.get( path : '/s', query : [wd:'Groovy'] )

assert html instanceof groovy.util.slurpersupport.GPathResult
assert html.HEAD.size() == 1
assert html.BODY.size() == 1


