/**
 * 
 */
package name.zhangmin.gw.io.rest.test

import groovyx.net.http.AsyncHTTPBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*

def http = new HTTPBuilder( 'http://api.openweathermap.org/data/2.5/' )

http.get( path: 'weather', query:[q: 'London', mode: 'xml'] ) { resp, xml ->
   println resp.status
   println "It is currently ${xml.weather.@value.text()} in London."
   println "The temperature is ${xml.temperature.@value.text()} degrees Kelvin"
}

def weather = new RESTClient( 'http://api.openweathermap.org/data/2.5/' )
def resp = weather.get( path: 'weather', query:[q: 'London', mode: 'xml'], contentType: TEXT,
  headers : [Accept : 'application/xml'] )
 
println resp.data.text       // print the XML

def http1 = new AsyncHTTPBuilder(
	poolSize : 4,
	uri : 'http://hc.apache.org',
	contentType : HTML )


def result1 = http1.get(path:'/') { resp1, html ->
println ' got async response!'
return html
}

assert result1 instanceof java.util.concurrent.Future

while ( ! result1.done ) {
println 'waiting...'
Thread.sleep(2000)
}

/* The Future instance contains whatever is returned from the response
closure above; in this case the parsed HTML data: */
def html = result1.get()
assert html instanceof groovy.util.slurpersupport.GPathResult