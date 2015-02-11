/**
 * 
 */
package name.zhangmin.gw.io.rest.test;

import static org.junit.Assert.*;

import groovy.util.XmlParser
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
import org.junit.After
import org.junit.Before
import org.junit.Test;

/**
 * @author ZhangMin.name
 *
 */
class RootResourceTest {
	def root = new RESTClient('http://localhost/rest/v1/')
	@Before
	void setUp() {
		
	}
	
	@Test
	void root() {
		def resp = root.get( path : '', contentType: TEXT, headers :[Accept : 'application/xml']) 
		println resp.status
		println resp.contentType
		//println resp.data.text
		def result = new XmlParser().parseText(resp.data.text)
		println "Published Resource:"
		result.link.each{ link ->
			println "${link.'@type'} ${link.text()}"
			assert link.'@type' in ['things', 'hello', 'thing-types', 'apps']
		}
	}
	
	@Test void 'get root resources'() {
		root.get(path : '') {
			resp, xml ->
			println resp.status
			xml.link.each {
				println "${it.'@type'} : ${it.text()}"
			}
		}
	}
	@After
	void cleanUp() {
		
	}
}
